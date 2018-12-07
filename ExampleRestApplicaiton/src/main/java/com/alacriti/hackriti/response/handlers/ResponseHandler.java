package com.alacriti.hackriti.response.handlers;

import javax.ws.rs.core.Response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.response.forms.ErrorResponseForm;

public class ResponseHandler {

	public Response handleResponse(RequestContext context, BaseResponseForm form) {

		// TODO need to check form == null condition
		if (context.isError() || form == null) {
			// error flow ...

			form = new ErrorResponseForm();
			form.setErrors(context.getErrors());

			return Response.status(404).entity(form).build();

		} else {
			// build success response ...

			return Response.status(200).entity(form).build();
		}

	}

}
