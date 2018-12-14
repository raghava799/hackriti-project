import {Injectable} from '@angular/core';
import {HttpHeaders} from '@angular/common/http';
import * as APP_URL_CONST from '../constants/app.url.const';

@Injectable()
export class GlobalService {


    constructor() {
    }

    static getBaseURL(): string {
        return APP_URL_CONST.BASE_URL + '/';
    }

    static getGoogleLoginURL(): string {
        const AUTHORIZATION = 'https://accounts.google.com/o/oauth2/v2/auth?client_id=799023887043-blv26lmp2q5ml00ti58' +
            '7q3bum508ks2a.apps.googleusercontent.com&response_type=code&include_granted_scopes' +
            '=true&state=state_parameter_passthrough_value&scope=' +
            'https://www.googleapis.com/auth/userinfo.email' + '&redirect_uri=http://dnpjnivbi5kz1.cloudfront.net&access_type=offline';
        return AUTHORIZATION;
    }

    static getToken(): string {
        console.log('Token get called');
        return sessionStorage.getItem('currentUser');
    }

    static setToken(currentUser: string) {
        console.log('Token set: ' + currentUser);
        sessionStorage.setItem('currentUser', currentUser);
    }

    static removeToken() {
        sessionStorage.removeItem('currentUser');
        sessionStorage.removeItem('admin');
        console.log('Token Removed');
    }

    static jwtHeader() {
        let headers = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/json');
        return {headers: headers};
    }

    handleError(error: any): Promise<any> {
        console.error('An error occurred with caseStatus: ' + error.status, error);
        console.log(error);
        return Promise.reject(error.message || error);
    }


}
