package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSlotRequestForm implements BaseRequestForm, Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2063200884208108789L;

	@JsonProperty("employee_id")
	private String employee_id;
	
	@JsonProperty("date")
	private String date;
	
	
	@JsonProperty("slot_number")
	private String slot_number;
	
	

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



	@Override
	public RequestContext validate(RequestContext context) {
		
		if (this.employee_id == null || !Validations.isValidEmployeeNumber(this.employee_id)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.EMPLOYEE_NUMBER, context);
		}
		
		if (this.date == null || !Validations.isValidDate(this.date)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.DATE, context);
		}
		
		if (!(this.slot_number == null) && !Validations.isValidSlotNumber(this.slot_number)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.SLOT_NUMBER, context);
		}

		return context;
	}

}
