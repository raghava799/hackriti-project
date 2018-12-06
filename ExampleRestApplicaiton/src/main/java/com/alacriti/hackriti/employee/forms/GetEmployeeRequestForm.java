package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import com.alacriti.hackriti.context.RequestContext;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class GetEmployeeRequestForm implements BaseRequestForm, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2494295870236378024L;

	@JsonProperty("employee_mail")
	private String employee_mail;

	public String getEmployee_mail() {
		return employee_mail;
	}

	public void setEmployee_mail(String employee_mail) {
		this.employee_mail = employee_mail;
	}

	@Override
	public RequestContext validate(RequestContext context) {

		return context;

	}

}
