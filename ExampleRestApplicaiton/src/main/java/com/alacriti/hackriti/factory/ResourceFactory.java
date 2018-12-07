package com.alacriti.hackriti.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alacriti.hackriti.bo.EmployeeApiHandler;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.utils.response.EmployeeResponseGenerator;

public class ResourceFactory {

	final static Logger logger = Logger.getLogger(ResourceFactory.class);

	public static Map<String, Object> apiHandlers = new HashMap<String, Object>();

	public static Map<String, Object> responseGenerators = new HashMap<String, Object>();

	public static Map<String, Object> getApiHandlers() {
		return apiHandlers;
	}

	public static Map<String, Object> getResponseGenerators() {
		return responseGenerators;
	}

	public ResourceFactory() {

		System.out.println("************** Initializing ResourceFactory ... ****************");
		logger.info("************** Initializing ResourceFactory ... ****************");
		loadApiHandlers();
		loadResponseGenerators();
	};

	private void loadResponseGenerators() {

		responseGenerators.put(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, new EmployeeResponseGenerator());
	}

	private void loadApiHandlers() {

		apiHandlers.put(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, new EmployeeApiHandler());
	}

}
