import {Component, OnInit} from '@angular/core';
import {HttpService} from '../httpService';


@Component({
    selector: 'app-dashboard',
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent implements OnInit {
    isOwner: false;
    isNonOwner: false;
    employeeDetails: Object;
    minDate: Date;
    maxDate: Date;
    selectedDate: Date;

    constructor(private http: HttpService) {
        const date = new Date();
        this.minDate = new Date();
        // this.maxDate = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        this.selectedDate = new Date();
    }

    ngOnInit() {
        this.http.getEmployeeDetails().then(res => {
            console.log(res);
            this.employeeDetails = res;
        });
    }

    bookSlot() {

    }

    freeSlot() {

    }
    onDateChange() {
        const month = this.selectedDate .getMonth() + 1;
        const day = this.selectedDate .getDate();
        const year = this.selectedDate .getFullYear();
        console.log(month + '/' + day + '/' + year);
    }
}
