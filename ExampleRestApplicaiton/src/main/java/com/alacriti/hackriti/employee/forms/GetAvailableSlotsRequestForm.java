package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import javax.ws.rs.PathParam;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAvailableSlotsRequestForm implements BaseRequestForm, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1585739870081782668L;
	
	@JsonProperty("date")
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public RequestContext validate(RequestContext context) {
		
		if (this.date == null || !Validations.isValidDate(this.date)) {

			context.setError(true);
			Validations.addErrorToContext(StringConstants.ReqeustConstants.DATE, context);
		}
		
		return context;
	}

}
