package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import java.util.List;

import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.Error;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"parking_info"})
public class GetAailableSlotsResponseForm implements BaseResponseForm, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1718843665373665568L;

	
	@JsonProperty("slots")
	private List<SlotsResponseForm> slots;
	
	
	public List<SlotsResponseForm> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotsResponseForm> slots) {
		this.slots = slots;
	}

	@Override
	public void setErrors(List<Error> errors) {
		// TODO Auto-generated method stub
		
	}

}
