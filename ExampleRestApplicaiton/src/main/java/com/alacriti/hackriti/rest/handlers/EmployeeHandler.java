package com.alacriti.hackriti.rest.handlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.utils.constants.StringConstants;

@Path("parking")
public class EmployeeHandler {

	final static Logger logger = Logger.getLogger(EmployeeHandler.class);

	@GET
	@Path("/health")
	public Response healthChecker() {

		logger.info("log4j is initialized ....!!!");
		return Response.status(200).entity("Application is up....!").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getemployee")
	public Response getEmployeeDetails(GetEmployeeRequestForm employeeForm) {

		logger.info("got email in request :" + employeeForm.getEmployee_mail_id());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS);

		Response response = BaseRequestHandler.process(requestContext, employeeForm);

		return response;
		// return Response.status(200).entity(response).build();
	}
}
