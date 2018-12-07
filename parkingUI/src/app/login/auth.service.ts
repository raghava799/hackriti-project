import {HttpClient, HttpRequest} from '@angular/common/http';
import {Router} from '@angular/router';
import {GlobalService} from './global.service';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class AuthService {
  cachedRequests: Array<HttpRequest<any>> = [];
  readonly renewTokenUrl = GlobalService.getBaseURL() + 'oauth/refreshToken';


  constructor(private http: HttpClient,
              private router: Router) {}

  public collectFailedRequest(request): void {
    this.cachedRequests.push(request);
  }

  public retryFailedRequests(req: HttpRequest<any>): Observable<any> {
    console.log('retryFailedRequests()', req);
    return this.http.request(req).map(originalRes => {
      console.log('original response for failed Request: ,', originalRes);
      return originalRes;
    });
  }

  public getToken(): string {
    return GlobalService.getToken();
  }

  public setToken(currentUser: string) {
    GlobalService.setToken(currentUser);
  }

  public removeToken() {
    GlobalService.removeToken();
  }

  public goToLoginPage() {
    this.router.navigate(['/login']);
  }

  public renewToken(): Observable<string> {
    console.log('requesting to renew token');
    return this.http.get(this.renewTokenUrl, {headers: GlobalService.jwtHeader().headers, observe: 'response'}).do(
      res => {
        console.log('success response received for renew token');
        console.log(res);
        const token = res.headers.get('Authorization');
        if (token) {
          this.setToken(token);
        }
      },
      er => {
        console.log('error response received for renew token');
        console.log(er);
          this.removeToken();
          this.router.navigate(['/login']);
      }
    ).map(res => 'success');
  }


  shouldSkip(reqUrl: string): boolean {
    if (GlobalService.getBaseURL()) {
      const reqPath = this.getUrlPath(reqUrl);
      console.log(`reqUrl: ${reqUrl}, reqPath: ${reqPath}`);
      let flag = false;
      this.getSkipUrls().forEach(urlPath => {
        if (urlPath === reqPath) {
          console.log(`will not try reNew token because path matched with: ${urlPath}`);
          flag = true;
        }
      });
      return flag;
    } else {
      return true;
    }
  }

  getSkipUrls(): string[] {
    const urls = [];
    urls.push(this.getUrlPath(GlobalService.getBaseURL() + 'oauth/refreshToken'));
    urls.push(this.getUrlPath(GlobalService.getBaseURL() + 'oauth/login'));
    urls.push(this.getUrlPath(GlobalService.getBaseURL() + 'oauth/login/mfa'));
    urls.push(this.getUrlPath('iac_ui/assets/config/config.json'));

    return urls;
  }

  private getUrlPath(reqUrl: string): string {
    return reqUrl.indexOf('http') > -1 ? new URL(reqUrl).pathname : reqUrl.split('?')[0];
  }
}
