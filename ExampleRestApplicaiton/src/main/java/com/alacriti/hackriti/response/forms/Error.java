package com.alacriti.hackriti.response.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184061176018062570L;

	@JsonProperty("error_message")
	private String error_message;

	@JsonProperty("error_field")
	private String error_field;

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getError_field() {
		return error_field;
	}

	public void setError_field(String error_field) {
		this.error_field = error_field;
	}

}
