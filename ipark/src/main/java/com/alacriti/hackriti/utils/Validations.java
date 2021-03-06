package com.alacriti.hackriti.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.response.forms.Error;
import com.alacriti.hackriti.utils.constants.StringConstants;

public class Validations {

	public static boolean isValidEmail(String email) {

		return (email.length() <= 100) && (Pattern.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email));
	}
	
	public static boolean isValidEmployeeNumber(String empId) {

		return (Pattern.matches("^[A-Za-z0-9]{1,30}$", empId));
	}
	
	public static boolean isValidSlotNumber(String slot) {

		return (Pattern.matches("^[A-Za-z0-9]{1,30}$", slot));
	}
	
	public static boolean isValidDate(String date) {

		return (Pattern.matches("^(?:[0-9]{2})?[0-9]{2}/[0-3]?[0-9]/[0-3]?[0-9]$", date));
	}


	public static RequestContext addErrorToContext(String errorField,String errorMsg, RequestContext context) {

		Error error = new Error();
		error.setError_field(errorField);
		// will be same for all
		if(errorMsg==null){
			error.setError_message(StringConstants.ErrorConstants.ERROR_MESSAGE);
		}
		else{
			error.setError_message(errorMsg);
		}

		if (context.getErrors() != null && context.getErrors().size() > 0) {

			context.getErrors().add(error);
		} else {

			List<Error> errors = new ArrayList<Error>();
			errors.add(error);
			context.setErrors(errors);
		}
		return context;
	}
}
