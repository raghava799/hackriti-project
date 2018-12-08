import {Injectable, Injector} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/observable/empty';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

import 'rxjs/add/operator/take';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/switchMap';

import 'rxjs/add/operator/mergeMap';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {catchError} from 'rxjs/operators';
import {EMPTY, throwError} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  isRefreshingToken = false;
  tokenSubject: BehaviorSubject<string> = new BehaviorSubject<string>(null);

  constructor(private injector: Injector) {
  }

  addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
    return req.clone({setHeaders: {Authorization: token}});
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // console.log('Yo!! passing through AuthInterceptor');
    const authService = this.injector.get(AuthService);
    const authHeader = authService.getToken();


    // const authReq = req.clone({headers: req.headers.set('Authorization', authHeader)});
    let authReq = req;
    if (authHeader) {
      authReq = this.addToken(req, authHeader);
    }

    if (authService.shouldSkip(authReq.url)) {
      return next.handle(authReq);
    }

    return next.handle(authReq)
      .pipe(
      catchError(error => {
        console.log('error caught in interceptor');
        console.log(error);
        if (error instanceof HttpErrorResponse) {
          console.log('This error is instanceof HttpErrorResponse');
          switch ((<HttpErrorResponse>error).status) {
            case 400:
              console.log('This error is with status 400');
              return this.handle400Error(error, authService);
            case 401:
              console.log('This error is with status 401');
              return this.handle401Error(req, next, authService);
          }
        }
        return this.getErrorObservable(error);
      })
    );

  }

  getErrorObservable(error): Observable<any> {
    return throwError(error);
  }

  handle400Error(error, authService: AuthService) {
    if (error && error.status === 400 && error.error && error.error.error === 'invalid_grant') {
      // If we get a 400 and the error message is 'invalid_grant', the token is no longer valid so logout.
      return this.logoutUser(authService, error);
    }
    return this.getErrorObservable(error);
  }

  handle401Error(req: HttpRequest<any>, next: HttpHandler, authService: AuthService) {
    if (!this.isRefreshingToken) {
      this.isRefreshingToken = true;

      // Reset here so that the following requests wait until the token
      // comes back from the refreshToken call.
      this.tokenSubject.next(null);

      return authService.renewToken()
        .switchMap((res) => {
          console.log('response from refresh token,', res);
          console.log('requesting back after refresh token,', req);
          this.isRefreshingToken = false;
          this.tokenSubject.next(res);
          return next.handle(this.addToken(req, authService.getToken()));
        })
        .pipe(catchError(error => {
          // If there is an exception calling 'refreshToken', bad news so logout.
          console.log('Error from refresh token,', error);
          this.isRefreshingToken = false;
          return this.logoutUser(authService, error);
        }));
    } else {
      // return next.handle(this.addToken(req, authService.getToken()));
      return this.tokenSubject
        .filter(token => token != null)
        .take(1)
        .switchMap(token => {
          return next.handle(this.addToken(req, authService.getToken()));
        });
    }
  }


  logoutUser(authService: AuthService, error) {
    // Route to the login page (implementation up to you)
    authService.removeToken();
    authService.goToLoginPage();
    return this.getErrorObservable(error);
  }

}

