import {Component, OnInit} from '@angular/core';
import * as APP_CONST from '../constants/app.const';
import * as APP_URL_CONST from '../constants/app.url.const';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AutherizationResponse} from '../models/authorizationResponse';

@Component({
    selector: 'app-top-nav',
    templateUrl: 'topnav.component.html'
})
export class TopnavComponent implements OnInit {
    constructor(private httpClient: HttpClient) {
    }

    ngOnInit() {
        // this.getAccessToken();
    }

    /*getAccessToken() {

        const params = new URLSearchParams(window.location.search);
        const code = params.get('code');

        const body = new URLSearchParams();
        body.set('code', code);
        body.set('client_id', APP_CONST.clientId);
        body.set('client_secret', APP_CONST.clientSecret);
        body.set('redirect_uri', APP_CONST.redirectURL);
        body.set('grant_type', APP_CONST.grantType);

        const options = {
            headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
        };
        this.httpClient.post<AutherizationResponse>(APP_URL_CONST.ACCESS_TOKEN, body.toString(), options).toPromise().then(res => {
            console.log(res);
            // to do api call
            this.getProfile(res.access_token);
        }).catch((error) => {
            console.log(error);
        });
    }
    getProfile(accessToken) {
        const url = APP_URL_CONST.PROFILE + accessToken;
        const options = {
            headers: new HttpHeaders().set('content-type', 'text' )
        };
        return this.httpClient.get(url, options).toPromise().then(res => {
            console.log(res);
            // to do api call
        });
    }
*/
}
