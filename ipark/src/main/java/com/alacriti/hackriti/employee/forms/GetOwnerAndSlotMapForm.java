package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;
import java.util.List;

import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.Error;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOwnerAndSlotMapForm implements BaseResponseForm, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1718843665373665568L;

	
	@JsonProperty("ownerslotmap")
	private List<EmployeeResponseForm> ownerSlotsMap;
	
	
	public List<EmployeeResponseForm> getOwnerSlotsMap() {
		return ownerSlotsMap;
	}

	public void setOwnerSlotsMap(List<EmployeeResponseForm> ownerSlotsMap) {
		this.ownerSlotsMap = ownerSlotsMap;
	}

	@Override
	public void setErrors(List<Error> errors) {
		// TODO Auto-generated method stub
		
	}

}
