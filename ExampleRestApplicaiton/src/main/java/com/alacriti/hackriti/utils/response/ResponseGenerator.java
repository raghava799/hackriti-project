package com.alacriti.hackriti.utils.response;

import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.BaseResponse;
import com.alacriti.hackriti.vo.Employee;

public class ResponseGenerator {

	public static BaseResponseForm getResponse(BaseResponse response) {

		BaseResponseForm form = null;

		if (response instanceof Employee) {

			form = (BaseResponseForm) enrichEmployeeForm((Employee) response);
		}

		return form;
	}

	private static EmployeeResponseForm enrichEmployeeForm(Employee response) {

		EmployeeResponseForm form = new EmployeeResponseForm();

		form.setDate_of_joining(response.getDateOfJoining());
		form.setEmployee_id(response.getEmployeeId());
		form.setEmployee_mail_id(response.getEmployeeMail());
		form.setEmployee_name(response.getEmployeeName());
		form.setEmployee_role(response.getEmployeeRole());

		return form;
	}

}
