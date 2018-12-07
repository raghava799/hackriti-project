import {Component, OnInit} from '@angular/core';
import * as APP_URL_CONST from '../constants/app.url.const';
import {HttpClient} from '@angular/common/http';


@Component({
    selector: 'app-dashboard',
    templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {
    isOwner: false;
    isNonOwner: false;
    employeeDetails: any;

    constructor(private http: HttpClient) {
    }

    ngOnInit() {
        const body = {
            employee_mail: 'asha@alacriti.co.in'
        };
        this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.GET_EMPLOYEE, body).toPromise().then(res => {
            console.log(res);
            this.employeeDetails = res;
        });
    }

    bookSlot() {

    }

    freeSlot() {

    }
}
