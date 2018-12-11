package com.alacriti.hackriti.employee.forms;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManageSlotRequestForm extends GetSlotRequestForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5499975462513362631L;
	@JsonProperty("parker_id")
	private String parker_id;

	public String getParker_id() {
		return parker_id;
	}

	public void setParker_id(String parker_id) {
		this.parker_id = parker_id;
	}
	
	public RequestContext validate(RequestContext context) {
		
		if (!(this.parker_id == null) && !Validations.isValidEmployeeNumber(this.parker_id)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.OWNER_ID,StringConstants.ErrorConstants.ERROR_MESSAGE, context);
		}
		if (super.getEmployee_id() == null || !Validations.isValidEmployeeNumber(super.getEmployee_id())) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.EMPLOYEE_NUMBER, StringConstants.ErrorConstants.ERROR_MESSAGE,context);
		}
		
		if (super.getDate() == null || !Validations.isValidDate(super.getDate())) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.DATE,StringConstants.ErrorConstants.ERROR_MESSAGE, context);
		}
		
		if (super.getSlot_number() == null || !Validations.isValidSlotNumber(super.getSlot_number())) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.SLOT_NUMBER, StringConstants.ErrorConstants.ERROR_MESSAGE,context);
		}

		return context;
	}

}
