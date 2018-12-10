import {Component, OnInit} from '@angular/core';
import {HttpService} from '../httpService';
import {Employee} from '../models/employee';
import {ThemePalette} from '@angular/material';


@Component({
    selector: 'app-dashboard',
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent implements OnInit {
    isOwner = false;
    employeeDetails: Employee;
    minDate: Date;
    maxDate: Date;
    displayableSelectedDate: Date;
    selectedDate: string;
    loggedInUserEmail: string;
    availableSlots: {};

    constructor(private http: HttpService) {
        if (sessionStorage.getItem('admin')) {
            const admin = JSON.parse(sessionStorage.getItem('admin'));
            this.loggedInUserEmail = admin['emailId'];
            console.log(admin);
        }
        const date = new Date();
        this.minDate = new Date();
        // this.maxDate = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        this.displayableSelectedDate = new Date();
        this.changeDateFormat();
    }

    ngOnInit() {
        this.getEmployeeDetails(this.loggedInUserEmail);
    }

    getEmployeeDetails(email) {
        this.http.getEmployeeDetails(email).then(res => {
            this.employeeDetails = res;
            console.log(this.employeeDetails);
            if (this.employeeDetails.employee_role === 'owner') {
                this.isOwner = true;
                this.getOwnerSlotDetails();
            } else if (this.employeeDetails.employee_role === 'user') {
                this.getUserSlotDetails();
            }
        });
    }

    bookSlot() {
        this.http.bookSlot({}).then(res => {
            console.log(res);
        });

    }

    cancelUserSlot() {
        this.http.cancelUserSlot({}).then(res => {
            console.log(res);
        });
    }

    cancelOwnerSlot() {
        this.http.cancelOwnerSlot({}).then(res => {
            console.log(res);
        });
    }

    getAvailableSlots() {
        const dateDetails = {
            date: this.selectedDate
        };
        this.http.getAvailableSlots(dateDetails).then(res => {
            console.log(res);
        });
    }

    getOwnerSlotDetails() {
        const slotDetails = {
            employee_id: this.employeeDetails.employee_id,
            date: this.selectedDate,
            slot_number: this.employeeDetails.parking_info.parking_slot_number
        };
        this.http.getOwnerSlot(slotDetails).then(res => {
            if (res['parker_id'] && res['parking_type']) {

            }
            console.log(res);
        });
    }

    getUserSlotDetails() {
        this.http.getUserSlot({}).then(res => {
            console.log(res);
        });
    }

    changeDateFormat() {
        const month = this.displayableSelectedDate.getMonth() + 1;
        const day = this.displayableSelectedDate.getDate();
        const year = this.displayableSelectedDate.getFullYear();
        this.selectedDate = year + '/' + month + '/' + day;
        console.log(month + '/' + day + '/' + year);
    }

    onDateChange() {
        this.changeDateFormat();
        if (this.isOwner) {
            this.getOwnerSlotDetails();
        }
    }
}
