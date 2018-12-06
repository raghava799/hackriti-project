package com.alacriti.hackriti.response.handlers;

import javax.ws.rs.core.Response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.ErrorResponseForm;
import com.alacriti.hackriti.utils.response.ResponseGenerator;
import com.alacriti.hackriti.vo.BaseResponse;

public class ResponseHandler {

	public Response handleResponse(RequestContext context, BaseResponse baseResponse) {

		BaseResponseForm form = null;

		if (context.isError()) {
			// error flow

			form = new ErrorResponseForm();
			form.setErrors(context.getErrors());

			return Response.status(404).entity(form).build();

		} else {
			// build success response

			form = ResponseGenerator.getResponse(baseResponse);

			return Response.status(200).entity(form).build();
		}

	}

}
