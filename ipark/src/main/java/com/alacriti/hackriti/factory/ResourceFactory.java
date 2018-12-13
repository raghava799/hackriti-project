package com.alacriti.hackriti.factory;

import java.util.HashMap;
import java.util.Map;

import com.alacriti.hackriti.bo.*;
import com.alacriti.hackriti.calendar.api.CancelCalendarEventApiHandler;
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

	public static Map<String, Object> getApiHandlers() {
		return apiHandlers;
	}

	public static Map<String, Object> getResponseGenerators() {
		return responseGenerators;
	}


	public ResourceFactory() {

		System.out.println("************** Initializing ResourceFactory ... ****************");
		logger.info("************** Initializing ResourceFactory ... ****************");
		loadApiHandlers();
		loadResponseGenerators();
	};

	private void loadResponseGenerators() {

		responseGenerators.put(StringConstants.ApiConstants.GET_EMPLOYEE_DETAILS, new EmployeeResponseGenerator());

		responseGenerators.put(StringConstants.ApiConstants.GET_OWNER_SLOT, new SlotDetailsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.GET_USER_SLOT, new SlotDetailsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.GET_AVAILABLE_SLOTS,
				new GetAvailableSlotsResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.BOOK_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.CANCEL_OWNER_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.CANCEL_USER_SLOT, new SlotResponseGenerator());
		responseGenerators.put(StringConstants.ApiConstants.DENY_AND_REBOOK_SLOT, new SlotResponseGenerator());
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
//		apiHandlers.put(StringConstants.ApiConstants.PUSH_CALENDAR_EVENT, new CreateCalendarEventApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.EMP_SLOT_ALLOCATION, new EmpSlotAllocation());
		apiHandlers.put(StringConstants.ApiConstants.CANCEL_CALENDAR_EVENT, new CancelCalendarEventApiHandler());
		apiHandlers.put(StringConstants.ApiConstants.DENY_AND_REBOOK_SLOT, new DenyAndRebookApiHandler());

	}

}
