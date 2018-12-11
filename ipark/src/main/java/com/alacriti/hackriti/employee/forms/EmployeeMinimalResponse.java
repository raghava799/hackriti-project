package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeMinimalResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4657273045002147390L;

	@JsonProperty("employee_id")
	private String employee_id;
	
	@JsonProperty("employee_number")
	private String employee_number;

	@JsonProperty("employee_mail_id")
	private String employee_mail_id;

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

	public String getEmployee_number() {
		return employee_number;
	}

	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}

	public String getEmployee_mail_id() {
		return employee_mail_id;
	}

	public void setEmployee_mail_id(String employee_mail_id) {
		this.employee_mail_id = employee_mail_id;
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

}
