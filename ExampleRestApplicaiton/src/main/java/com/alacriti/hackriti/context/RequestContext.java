package com.alacriti.hackriti.context;

import java.util.List;

import com.alacriti.hackriti.response.forms.Error;

public class RequestContext {

	private List<Error> errors;

	private boolean isError;

	public List<Error> getErrors() {
		return this.errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

}
