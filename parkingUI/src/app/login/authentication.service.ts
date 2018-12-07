import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import {GlobalService} from './global.service';

@Injectable()
export class AuthenticationService {


  private url = GlobalService.getBaseURL() + 'oauth';

  constructor(private http: HttpClient) {
  }

  login(code: string): Promise<any> {
    const url = `${this.url}/login?code=${code}`;
    return this.http.get(url, {headers: GlobalService.jwtHeader().headers, observe: 'response'}).toPromise();
  }

  getMyself(): Promise<any> {
    const url = `${this.url}`;
    return this.http.get(url, GlobalService.jwtHeader()).toPromise();
  }

  getAuthorizationHeader() {
    return GlobalService.getToken();
  }


    logout() {
      GlobalService.removeToken();
    }
}
