package com.alacriti.hackriti.resource.handlers;

import javax.ws.rs.core.Response;

import com.alacriti.hackriti.bo.EmployeeBO;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.BaseRequestForm;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.response.handlers.ResponseHandler;
import com.alacriti.hackriti.vo.BaseResponse;

public class BaseRequestHandler {

	public static Response process(RequestContext context, BaseRequestForm form) {

		preProcess(context, form);

		BaseResponse baseResponse = null;

		if (!context.isError()) {

			baseResponse = doProcess(form, context);
		}

		Response response = postProcess(context, baseResponse);

		return response;
	}

	public static RequestContext preProcess(RequestContext context, BaseRequestForm form) {

		context = form.validate(context);
		return context;
	}

	public static BaseResponse doProcess(BaseRequestForm form, RequestContext context) {

		BaseResponse response = null;

		if (form instanceof GetEmployeeRequestForm) {

			EmployeeBO empBo = new EmployeeBO();

			try {
				response = empBo.getEmployeeDetails((GetEmployeeRequestForm) form);
			} catch (BOException e) {

				context.setError(true);
				e.printStackTrace();
			}
		}

		return response;
	}

	public static Response postProcess(RequestContext context, BaseResponse baseResponse) {

		ResponseHandler responseHandler = new ResponseHandler();

		return responseHandler.handleResponse(context, baseResponse);
	}

}
