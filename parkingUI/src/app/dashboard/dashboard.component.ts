import {Component, OnInit} from '@angular/core';
import {HttpService} from '../httpService';
import {Employee} from '../models/employee';


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
    availableSlots: any;
    parkerEmployeeDetails: Employee;
    isloading = true;
    isOwnerSlotBookedByUser = false;
    isSlotsAvailable = false;
    tableHeaders = ['Parking Type', 'Parking Level', 'Parking Slot Number', 'Owner Email', 'Owner Name'];

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

    getEmployeeDetails(employeeDetails) {
        const body = {
            employee_mail_id: employeeDetails
        };
        this.http.getEmployeeDetails(body).then(res => {
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

    bookSlot(slotDetails) {
        console.log(slotDetails)
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
            if (res['slots']) {
                this.modifyAvailableSlotsData(res['slots']);
                this.isSlotsAvailable = true;
            }
            this.isloading = false;
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
                this.http.getEmployeeDetails({employee_id: res['parker_id']}).then(response => {
                    this.parkerEmployeeDetails = response;
                    this.isOwnerSlotBookedByUser = true;
                });
            }
            console.log(res);
            this.isloading = false;
        });
    }

    getUserSlotDetails() {
        const employeeDetails = {
            date: this.selectedDate,
            employee_id: this.employeeDetails.employee_id
        };
        this.http.getUserSlot(employeeDetails).then(res => {
            console.log(res);
            if (res['parking_level'] && res['parking_type'] && res['parker_id'] && res['slot_number']) {
                this.isOwnerSlotBookedByUser = true;
                this.isloading = false;
            } else {
                this.getAvailableSlots();
            }
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
        } else {
            this.getUserSlotDetails();
        }
    }

    modifyAvailableSlotsData(slots) {
        this.availableSlots = [];
        slots.forEach(slot => {
            const slotDetails = {
                parking_type: slot['parking_type'],
                parking_level: slot['parking_level'],
                slot_number: slot['slot_number'],
                owner_email: slot['owner']['employee_mail_id'],
                owner_name: slot['owner']['employee_name']
            };
            this.availableSlots.push(slotDetails);
            console.log(this.availableSlots);

        });
    }
}