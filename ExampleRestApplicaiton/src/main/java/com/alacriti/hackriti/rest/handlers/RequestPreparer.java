package com.alacriti.hackriti.rest.handlers;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.BaseRequestForm;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.vo.Employee;

public class RequestPreparer {

	public void prepareRequest(RequestContext context, BaseRequestForm form) {

		if (form instanceof GetEmployeeRequestForm) {

			Employee employee;

			if (context.getEmployee() == null) {

				employee = new Employee();

			} else {

				employee = context.getEmployee();
			}

			employee.setEmployeeMail(((GetEmployeeRequestForm) form).getEmployee_mail_id());

			context.setEmployee(employee);

		}
	}

}
