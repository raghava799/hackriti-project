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

	public static RequestContext addErrorToContext(String errorField, RequestContext context) {

		Error error = new Error();
		error.setError_field(errorField);
		// will be same for all
		error.setError_message(StringConstants.ErrorConstants.ERROR_MESSAGE);

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
