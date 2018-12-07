package com.alacriti.hackriti.context;

import java.util.List;

import com.alacriti.hackriti.response.forms.Error;
import com.alacriti.hackriti.vo.Employee;

public class RequestContext {

	private ContextContainer contextContainer = new ContextContainer();

	private String apiName;

	private List<Error> errors;

	private boolean isError;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public void setEmployee(Employee employee) {

		this.contextContainer.setEmployee(employee);
	}

	public Employee getEmployee() {

		return this.contextContainer.getEmployee();
	}

	public ContextContainer getContextContainer() {
		return contextContainer;
	}

	public void setContextContainer(ContextContainer contextContainer) {
		this.contextContainer = contextContainer;
	}

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
