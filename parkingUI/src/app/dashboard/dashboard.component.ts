import {Component, Inject, OnInit} from '@angular/core';
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
    ownerEmployeeDetails: Employee;
    isloading = true;
    isOwnerSlotBookedByUser = false;
    isSlotsAvailable = false;
    isOwnerSlotFreedAndNotBooked = false;
    tableHeaders = ['Parking Type', 'Parking Level', 'Parking Slot Number', 'Owner Email', 'Owner Name'];
    selectedSlot: Object;

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
        }).catch(error => {
            console.log(error);
            throw error;
        });
    }

    bookSlot() {
        this.isloading = true;
        const slotDetails = {
            parker_id: this.employeeDetails.employee_id,
            employee_id: this.selectedSlot['owner_id'],
            date: this.selectedSlot['date'],
            slot_number: this.selectedSlot['slot_number']
        };
        this.http.bookSlot(slotDetails).then(res => {
            console.log(res);
            if (res && res['owner_id']) {
                this.http.getEmployeeDetails({employee_id: res['owner_id']}).then(response => {
                    this.isOwnerSlotBookedByUser = true;
                    this.ownerEmployeeDetails = response;
                    this.isloading = false;
                });
            }
        }).catch(error => {
            this.isOwnerSlotBookedByUser = false;
            console.log(error);
            throw error;
        });

    }

    cancelUserSlot() {
        this.isloading = true;
        const slotDetails = {
            parker_id: this.employeeDetails.employee_id,
            employee_id: this.ownerEmployeeDetails.employee_id,
            date: this.selectedDate,
            slot_number: this.ownerEmployeeDetails.parking_info.parking_slot_number
        };
        this.http.cancelUserSlot(slotDetails).then(res => {
            if (res) {
                this.isOwnerSlotBookedByUser = false;
                this.getAvailableSlots();
            }
            console.log(res);
        }).catch(error => {
            console.log(error);
            throw error;
        });
    }

    cancelSlot() {
        if (this.isOwner) {
            this.cancelOwnerSlot();
        } else {
            this.cancelUserSlot();
        }
    }

    cancelOwnerSlot() {
        this.isloading = true;
        const slotDetails = {
            employee_id: this.employeeDetails.employee_id,
            date: this.selectedDate,
            slot_number: this.employeeDetails.parking_info.parking_slot_number

        };
        this.http.cancelOwnerSlot(slotDetails).then(res => {
            if (res['owner_id']) {
                this.getOwnerSlotDetails();
            } else {
                this.isOwnerSlotBookedByUser = false;
                this.isOwnerSlotFreedAndNotBooked = false;
            }
        }).catch(error => {
            console.log(error);
            throw error;
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
            } else {
                this.isSlotsAvailable = false;
            }
            this.isloading = false;
        }).catch(error => {
            console.log(error);
            throw error;
        });
    }

    getOwnerSlotDetails() {
        const slotDetails = {
            employee_id: this.employeeDetails.employee_id,
            date: this.selectedDate,
            slot_number: this.employeeDetails.parking_info.parking_slot_number
        };
        this.http.getOwnerSlot(slotDetails).then(res => {
            if (res['parking_type']) {
                if (res['parker_id']) {
                    this.http.getEmployeeDetails({employee_id: res['parker_id']}).then(response => {
                        this.parkerEmployeeDetails = response;
                        this.isOwnerSlotBookedByUser = true;
                        this.isOwnerSlotFreedAndNotBooked = false;
                    }).catch(error => {
                        console.log(error);
                        throw error;
                    });
                } else {
                    this.isOwnerSlotFreedAndNotBooked = true;

                }
            } else {
                this.isOwnerSlotFreedAndNotBooked = false;
                this.isOwnerSlotBookedByUser = false;
            }
            console.log(res);
            this.isloading = false;
        }).catch(error => {
            console.log(error);
            throw error;
        });
    }

    getUserSlotDetails() {
        this.availableSlots = [];
        this.isOwnerSlotBookedByUser = false;
        const employeeDetails = {
            date: this.selectedDate,
            employee_id: this.employeeDetails.employee_id
        };
        this.http.getUserSlot(employeeDetails).then(res => {
            console.log(res);
            if (res['parking_level'] && res['parking_type'] && res['parker_id'] && res['slot_number']) {
                this.http.getEmployeeDetails({employee_id: res['employee_id']}).then(response => {
                    this.ownerEmployeeDetails = response;
                    this.isOwnerSlotBookedByUser = true;
                    this.isloading = false;
                });
            } else {
                this.getAvailableSlots();
            }
        }).catch(error => {
            console.log(error);
            throw error;
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
        this.isloading = true;
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
                owner_name: slot['owner']['employee_name'],
                owner_id: slot['owner_id'],
                date: slot['date'],

            };
            this.availableSlots.push(slotDetails);
            console.log(this.availableSlots);

        });
    }

    availableSelectedSlot(slot) {
        this.selectedSlot = slot;
    }

    clearSelectedSlot() {
        this.selectedSlot = {};
    }
}
