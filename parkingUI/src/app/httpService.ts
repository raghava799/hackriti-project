import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as APP_URL_CONST from './constants/app.url.const';
import {Employee} from './models/employee';

@Injectable()
export class HttpService {


    constructor(private http: HttpClient) {
    }

    getEmployeeDetails(employeeDetails) {
        return this.http.post<Employee>(APP_URL_CONST.BASE_URL + APP_URL_CONST.GET_EMPLOYEE, employeeDetails).toPromise();
    }

    bookSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.ADD_SLOT, slotDetails).toPromise();
    }

    cancelUserSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.CANCEL_USER_SLOT, slotDetails).toPromise();

    }

    cancelOwnerSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.CANCEL_OWNER_SLOT, slotDetails).toPromise();

    }

    getAvailableSlots(date) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.AVAILABLE_SLOTS, date).toPromise();

    }

    getOwnerSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.OWNER_SLOT, slotDetails).toPromise();

    }

    getUserSlot(employeeDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.USER_SLOT, employeeDetails).toPromise();

    }

    denyAndRebookSlot(slotDetails) {
        return this.http.post(APP_URL_CONST.BASE_URL + APP_URL_CONST.DENY_AND_REBOOK_SLOT, slotDetails).toPromise();

    }

    handleError(error: any): Promise<any> {
        console.error('An error occurred with caseStatus: ' + error.status, error);
        console.log(error);
        return Promise.reject(error.message || error);
    }

}
