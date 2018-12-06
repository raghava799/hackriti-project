package com.alacriti.hackriti.resource.handlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;

@Path("/parking")
public class EmployeeHandler {

	final static Logger logger = Logger.getLogger(EmployeeHandler.class);

	@GET
	@Path("/{message}")
	public Response publishMessage(@PathParam("message") String msgStr) {
		logger.info("log4j is initialized ....!!!");
		String responseStr = "Received message: " + msgStr;
		return Response.status(200).entity(responseStr).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getemployee")
	public Response getEmployeeDetails(GetEmployeeRequestForm employeeForm) {

		logger.info("got email in request :" + employeeForm.getEmployee_mail());

		RequestContext requestContext = new RequestContext();

		Response response = BaseRequestHandler.process(requestContext, employeeForm);

		return response;
		// return Response.status(200).entity(response).build();
	}
}
