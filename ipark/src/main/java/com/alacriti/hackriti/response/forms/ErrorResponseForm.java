package com.alacriti.hackriti.response.forms;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseForm implements BaseResponseForm, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695141028554562072L;

	@JsonProperty("errors")
	private List<Error> errors;

	// = new ArrayList<Error>();

	@Override
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	// public List<Error> addError(Error error) {
	//
	// errors.add(error);
	// return errors;
	// }

}
