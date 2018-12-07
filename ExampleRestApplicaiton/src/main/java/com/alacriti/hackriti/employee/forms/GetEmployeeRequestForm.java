package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class GetEmployeeRequestForm implements BaseRequestForm, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2494295870236378024L;

	@JsonProperty("employee_mail_id")
	private String employee_mail_id;

	public String getEmployee_mail_id() {
		return employee_mail_id;
	}

	public void setEmployee_mail_id(String employee_mail) {
		this.employee_mail_id = employee_mail;
	}

	@Override
	public RequestContext validate(RequestContext context) {

		if (this.employee_mail_id == null || !Validations.isValidEmail(this.employee_mail_id)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.EMPLOYEE_MAIL_ID, context);
		}

		return context;

	}

}
