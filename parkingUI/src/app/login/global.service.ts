import {Injectable} from '@angular/core';
import { HttpHeaders} from '@angular/common/http';

@Injectable()
export class GlobalService {


    constructor() {}

    static getBaseURL(): string {
        return 'http://localhost:8080/ExampleRestApplicaiton/';
    }

    static getGoogleLoginURL(): string {
        const AUTHORIZATION = 'https://accounts.google.com/o/oauth2/v2/auth?client_id=560429940835-i9l86ctcptqektdh3vhffsk0fom3hmio.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/gmail.send+https://www.googleapis.com/auth/analytics.readonly+https://www.googleapis.com/auth/plus.login+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/plus.me&redirect_uri=http://localhost:4203&access_type=offline';
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
        console.log('Token Removed');
    }

    static jwtHeader() {
        let headers = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/json');
        return {headers: headers };
    }

    handleError(error: any): Promise<any> {
        console.error('An error occurred with caseStatus: ' + error.status, error);
        console.log(error);
        return Promise.reject(error.message || error);
    }

}
