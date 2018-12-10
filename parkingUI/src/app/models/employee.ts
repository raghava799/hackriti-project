import {ParkingInfo} from './parkingInfo';

export interface Employee {
    employee_id: string;
    employee_number: number;
    employee_mail_id: string;
    employee_name: string;
    date_of_joining: string;
    employee_role: string;
    parking_info: ParkingInfo;
}
