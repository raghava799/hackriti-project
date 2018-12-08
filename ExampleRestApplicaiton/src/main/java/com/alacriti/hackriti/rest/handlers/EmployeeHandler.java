package com.alacriti.hackriti.rest.handlers;

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
import com.alacriti.hackriti.employee.forms.GetAvailableSlotsRequestForm;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.employee.forms.GetSlotRequestForm;
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

		Response response = BaseRequestHandler.process(requestContext, employeeForm,
				StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, StringConstants.ApiConstants.GET_PARKING_DETAILS);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/owner/slot")
	public Response getSlot(GetSlotRequestForm slotForm) {

		logger.info("got email in request :" + slotForm.getEmployee_id());
		logger.info("got date in request :" + slotForm.getDate());
		logger.info("got slot_no in request :" + slotForm.getSlot_number());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.GET_OWNER_SLOT);

		Response response = BaseRequestHandler.process(requestContext, slotForm,StringConstants.ApiConstants.GET_OWNER_SLOT);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/slots/available")
	public Response getAvailableSlots(GetAvailableSlotsRequestForm form) {

		logger.info("got date in request :" + form.getDate());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.GET_AVAILABLE_SLOTS);

		Response response = BaseRequestHandler.process(requestContext, form,StringConstants.ApiConstants.GET_AVAILABLE_SLOTS);

		return response;
	}
}
