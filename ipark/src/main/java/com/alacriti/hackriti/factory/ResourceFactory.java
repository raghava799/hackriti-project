package com.alacriti.hackriti.factory;

import java.util.HashMap;
import java.util.Map;

import com.alacriti.hackriti.bo.*;
import com.alacriti.hackriti.calendar.api.CreateCalendarEventApiHandler;

import org.apache.log4j.Logger;

import com.alacriti.hackriti.delegate.EmpSlotAllocation;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.utils.response.EmpSlotAllocationResp;
import com.alacriti.hackriti.utils.response.EmployeeResponseGenerator;
import com.alacriti.hackriti.utils.response.GetAvailableSlotsResponseGenerator;
import com.alacriti.hackriti.utils.response.SlotDetailsResponseGenerator;
import com.alacriti.hackriti.utils.response.SlotResponseGenerator;

public class ResourceFactory {

	final static Logger logger = Logger.getLogger(ResourceFactory.class);

	public static Map<String, Object> apiHandlers = new HashMap<String, Object>();

	public static Map<String, Object> responseGenerators = new HashMap<String, Object>();

	private static Map<String, String> resourceEMails = new HashMap<String, String>();

	public static Map<String, Object> getApiHandlers() {
		return apiHandlers;
	}

	public static Map<String, Object> getResponseGenerators() {
		return responseGenerators;
	}

	public static Map<String, String> getResourceEMails() {
		return resourceEMails;
	}

	public ResourceFactory() {

		System.out.println("************** Initializing ResourceFactory ... ****************");
		logger.info("************** Initializing ResourceFactory ... ****************");
		loadApiHandlers();
		loadResponseGenerators();
		loadResourceEmails();
	};

	private void loadResourceEmails() {
		// loading resource mailId's with resourceId as key

		resourceEMails.put("1", "alacriti.co.in_3937343532363838393230@resource.calendar.google.com");
		resourceEMails.put("2", "alacriti.co.in_3937353734323231313731@resource.calendar.google.com");
		resourceEMails.put("3", "alacriti.co.in_3832393937383932333535@resource.calendar.google.com");
		resourceEMails.put("4", "alacriti.co.in_35363335323035363730@resource.calendar.google.com");
		resourceEMails.put("6", "alacriti.co.in_3137383734373933383632@resource.calendar.google.com");
		resourceEMails.put("7", "");
		resourceEMails.put("8", "");
		resourceEMails.put("9", "");
		resourceEMails.put("10", "");
		resourceEMails.put("11", "");
		resourceEMails.put("12", "");
	}

	private void loadResponseGenerators() {

		responseGenerators.put(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, new EmployeeResponseGenerator());

		responseGenerators.put(StringConstants.ApiConstants.GET_OWNER_SLOT, new SlotDetailsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.GET_USER_SLOT, new SlotDetailsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.GET_AVAILABLE_SLOTS,
				new GetAvailableSlotsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.BOOK_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.CANCEL_OWNER_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.CANCEL_USER_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.EMP_SLOT_ALLOCATION, new EmpSlotAllocationResp());

	}

	private void loadApiHandlers() {

		apiHandlers.put(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, new EmployeeApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.GET_PARKING_DETAILS, new ParkingHandler()
		);
		apiHandlers.put(StringConstants.ApiConstants.GET_OWNER_SLOT, new GetSlotApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.GET_USER_SLOT, new GetSlotApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.GET_AVAILABLE_SLOTS, new SearchAvailableSlotsApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.BOOK_SLOT, new BookSlotApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.CANCEL_OWNER_SLOT, new CancelSlotApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.CANCEL_USER_SLOT, new CancelSlotApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.PUSH_CALENDAR_EVENT, new CreateCalendarEventApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.EMP_SLOT_ALLOCATION, new EmpSlotAllocation());

	}

}
