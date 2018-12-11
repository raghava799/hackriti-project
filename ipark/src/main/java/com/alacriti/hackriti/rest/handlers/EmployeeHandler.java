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
import com.alacriti.hackriti.employee.forms.GetAvailableSlotsRequestForm;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.employee.forms.GetSlotRequestForm;
import com.alacriti.hackriti.employee.forms.ManageSlotRequestForm;
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
	public Response getOwnerSlot(GetSlotRequestForm slotForm) {

		logger.info("got email in request :" + slotForm.getEmployee_id());
		logger.info("got date in request :" + slotForm.getDate());
		logger.info("got slot_no in request :" + slotForm.getSlot_number());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.GET_OWNER_SLOT);

		Response response = BaseRequestHandler.process(requestContext, slotForm,
				StringConstants.ApiConstants.GET_OWNER_SLOT);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/slot")
	public Response getUserSlot(GetSlotRequestForm slotForm) {

		logger.info("got email in request :" + slotForm.getEmployee_id());
		logger.info("got date in request :" + slotForm.getDate());
		logger.info("got slot_no in request :" + slotForm.getSlot_number());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.GET_USER_SLOT);

		Response response = BaseRequestHandler.process(requestContext, slotForm,
				StringConstants.ApiConstants.GET_USER_SLOT);

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

		Response response = BaseRequestHandler.process(requestContext, form,
				StringConstants.ApiConstants.GET_AVAILABLE_SLOTS);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/book/slot")
	public Response bookSlot(ManageSlotRequestForm form) {

		logger.info("got getDate in request :" + form.getDate());
		logger.info("got getEmployee_id in request :" + form.getEmployee_id());
		logger.info("got getParker_id in request :" + form.getParker_id());
		logger.info("got getSlot_number in request :" + form.getSlot_number());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.BOOK_SLOT);

		Response response = BaseRequestHandler.process(requestContext, form, StringConstants.ApiConstants.BOOK_SLOT,
				StringConstants.ApiConstants.PUSH_CALENDAR_EVENT);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel/owner/slot")
	public Response cancelOwnerSlot(ManageSlotRequestForm form) {

		logger.info("got date in request :" + form.getDate());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.CANCEL_OWNER_SLOT);

		Response response = BaseRequestHandler.process(requestContext, form,
				StringConstants.ApiConstants.CANCEL_OWNER_SLOT,StringConstants.ApiConstants.CANCEL_CALENDAR_EVENT);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel/user/slot")
	public Response canceUserlSlot(ManageSlotRequestForm form) {

		logger.info("got date in request :" + form.getDate());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.CANCEL_USER_SLOT);

		Response response = BaseRequestHandler.process(requestContext, form,
				StringConstants.ApiConstants.CANCEL_USER_SLOT,StringConstants.ApiConstants.CANCEL_CALENDAR_EVENT);

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/slotallocation")
	public Response empSlotAllocation(ManageSlotRequestForm form) {

		logger.info("got date in request :" + form.getDate());

		RequestContext requestContext = new RequestContext();
		requestContext.setApiName(StringConstants.ApiConstants.EMP_SLOT_ALLOCATION);

		Response response = BaseRequestHandler.process(requestContext, form,
				StringConstants.ApiConstants.EMP_SLOT_ALLOCATION);

		return response;
	}
}
