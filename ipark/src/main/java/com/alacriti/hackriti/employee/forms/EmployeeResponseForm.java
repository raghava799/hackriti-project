package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;
import java.util.List;

import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeResponseForm extends EmployeeMinimalResponse implements BaseResponseForm, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8851848529640076341L;

	
	@JsonProperty("parking_info")
	private ParkingInfo parking_info;


	public ParkingInfo getParking_info() {
		return parking_info;
	}


	public void setParking_info(ParkingInfo parking_info) {
		this.parking_info = parking_info;
	}


	@Override
	public void setErrors(List<Error> errors) {
		// TODO Auto-generated method stub

	}

}
