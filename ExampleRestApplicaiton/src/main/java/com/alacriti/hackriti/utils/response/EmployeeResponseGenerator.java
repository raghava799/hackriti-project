package com.alacriti.hackriti.utils.response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.employee.forms.ParkingInfo;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.Employee;

public class EmployeeResponseGenerator implements BaseResponseFormGenerator {

	@Override
	public BaseResponseForm generateResponse(RequestContext context) {

		EmployeeResponseForm form = new EmployeeResponseForm();

		if (context.getContextContainer() != null && context.getContextContainer().getEmployee() != null) {

			ParkingInfo info = new ParkingInfo();

			Employee response = context.getContextContainer().getEmployee();

			form.setDate_of_joining(response.getDateOfJoining());
			form.setEmployee_number(response.getEmployeeNumber());
			form.setEmployee_mail_id(response.getEmployeeMail());
			form.setEmployee_name(response.getEmployeeName());
			form.setEmployee_role(response.getEmployeeRole());
			form.setEmployee_id(response.getEmployeeId());

			if (response.getParkingInfo() != null) {

				info.setParking_level(response.getParkingInfo().getParkingLevel());
				info.setParking_slot_id(response.getParkingInfo().getParkingSlotId());
				info.setParking_slot_number(response.getParkingInfo().getParkingSlotNumber());
				info.setParking_type(response.getParkingInfo().getParkingType());

			}
			form.setParking_info(info);
		}

		return form;
	}

}
