package com.alacriti.hackriti.rest.handlers;

import javax.ws.rs.core.Response;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.BaseRequestForm;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.factory.ResourceFactory;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.handlers.ResponseHandler;
import com.alacriti.hackriti.utils.response.BaseResponseFormGenerator;

public class BaseRequestHandler {

	public static Response process(RequestContext context, BaseRequestForm form) {

		// 1. preProcess does form level data validation and prepares the
		// request (form to java obj)

		// 2. doProcess does actual business logic

		// 3. postProcess does data ResponseGeneration (java obj to form) &
		// Error Handling

		preProcess(context, form);

		if (!context.isError()) {
			// calling doProcess if no errors found in request

			doProcess(context);
		}

		// postProcess gets called in all cases (success or error)

		Response response = postProcess(context);

		return response;
	}

	public static RequestContext preProcess(RequestContext context, BaseRequestForm form) {

		context = form.validate(context);

		RequestPreparer requestPreparer = new RequestPreparer();

		requestPreparer.prepareRequest(context, form);

		return context;
	}

	public static void doProcess(RequestContext context) {

		BaseApiHandler apiHandler = getApiHandler(context);

		try {
			apiHandler.handleRequest(context);

		} catch (BOException e) {

			context.setError(true);
			e.printStackTrace();
		}

	}

	private static BaseApiHandler getApiHandler(RequestContext context) {

		return (BaseApiHandler) ResourceFactory.getApiHandlers().get(context.getApiName());
	}

	public static Response postProcess(RequestContext context) {

		ResponseHandler responseHandler = new ResponseHandler();
		BaseResponseForm form = null;

		if (!context.isError()) {

			BaseResponseFormGenerator responseGenerator = getResponseFormGenerator(context);
			form = responseGenerator.generateResponse(context);
		}

		return responseHandler.handleResponse(context, form);
	}

	private static BaseResponseFormGenerator getResponseFormGenerator(RequestContext context) {

		return (BaseResponseFormGenerator) ResourceFactory.getResponseGenerators().get(context.getApiName());
	}

}
