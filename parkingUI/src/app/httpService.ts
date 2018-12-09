import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as APP_URL_CONST from './constants/app.url.const';

@Injectable()
export class HttpService {


    constructor(private http: HttpClient) {
    }

    getEmployeeDetails() {
        const body = {
            employee_mail_id: 'asha@alacriti.co.in'
        };
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.GET_EMPLOYEE, body).toPromise();
    }

    bookSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.BOOK_SLOT, slotDetails).toPromise();
    }
    cancelSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.CANCEL_SLOT, slotDetails).toPromise();

    }
    getAvailableSlots(employeeDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.AVAILABLE_SLOTS, employeeDetails).toPromise();

    }
    handleError(error: any): Promise<any> {
        console.error('An error occurred with caseStatus: ' + error.status, error);
        console.log(error);
        return Promise.reject(error.message || error);
    }

}
