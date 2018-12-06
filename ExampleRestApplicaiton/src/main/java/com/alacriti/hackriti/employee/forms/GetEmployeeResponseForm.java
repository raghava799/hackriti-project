package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;
import java.util.List;

import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetEmployeeResponseForm implements BaseResponseForm, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8851848529640076341L;

	@JsonProperty("employee_id")
	private String employee_id;

	@JsonProperty("employee_mail")
	private String employee_mail;

	@JsonProperty("employee_name")
	private String employee_name;

	@JsonProperty("date_of_joining")
	private String date_of_joining;

	@JsonProperty("employee_role")
	private String employee_role;

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_mail() {
		return employee_mail;
	}

	public void setEmployee_mail(String employee_mail) {
		this.employee_mail = employee_mail;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getDate_of_joining() {
		return date_of_joining;
	}

	public void setDate_of_joining(String date_of_joining) {
		this.date_of_joining = date_of_joining;
	}

	public String getEmployee_role() {
		return employee_role;
	}

	public void setEmployee_role(String employee_role) {
		this.employee_role = employee_role;
	}

	@Override
	public void setErrors(List<Error> errors) {
		// TODO Auto-generated method stub

	}

}
