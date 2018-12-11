package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;
import java.util.List;

import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SlotResponseForm implements BaseResponseForm, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1018055454928092562L;

	@JsonProperty("employee_id")
	private String employee_id;
	
	@JsonProperty("date")
	private String date;
	
	
	@JsonProperty("slot_number")
	private String slot_number;
	
	@JsonProperty("parker_id")
	private String parker_id;
	
	@JsonProperty("parking_level")
	private String parking_level;
	
	
	@JsonProperty("parking_type")
	private String parking_type;
	
	
	public String getEmployee_id() {
		return employee_id;
	}


	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}


	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getSlot_number() {
		return slot_number;
	}



	public void setSlot_number(String slot_number) {
		this.slot_number = slot_number;
	}



	public String getParker_id() {
		return parker_id;
	}



	public void setParker_id(String parker_id) {
		this.parker_id = parker_id;
	}



	public String getParking_level() {
		return parking_level;
	}



	public void setParking_level(String parking_level) {
		this.parking_level = parking_level;
	}



	public String getParking_type() {
		return parking_type;
	}



	public void setParking_type(String parking_type) {
		this.parking_type = parking_type;
	}
	



	@Override
	public void setErrors(List<Error> errors) {
		// TODO Auto-generated method stub
		
	}

}
