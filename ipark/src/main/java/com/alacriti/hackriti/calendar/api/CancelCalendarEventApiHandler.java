package com.alacriti.hackriti.calendar.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.CalendarDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.EventVO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

public class CancelCalendarEventApiHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {

		if (context.getCalendarEvent() == null) {
			System.out.println("GETTTING EVENT DETAILS :::::::: CancelCalendarEventApiHandler");
			getEventDetails(context);
		}
		if (!context.isError()) {

			System.out.println("going to cancel calendar event ....");
			cancelCalendarEvent(context, context.getCalendarEvent());
		} else {
			return;
		}
	}

	public void getEventDetails(RequestContext context) throws BOException {

		CalendarDAO dao = new CalendarDAO();
		System.out.println("Cancel Calender Handler. getEventDetails()");
		try {

			EventVO event = null;
			if (StringConstants.ApiConstants.CANCEL_USER_SLOT.equals(context.getApiName())
					|| StringConstants.ApiConstants.DENY_AND_REBOOK_SLOT.equals(context.getApiName())) {
				event = dao.getEventDetails(context);
			}

			if (StringConstants.ApiConstants.CANCEL_OWNER_SLOT.equals(context.getApiName())
					|| StringConstants.ApiConstants.CANCEL_CALENDAR_EVENT.equals(context.getApiName())) {
				event = dao.getEventDetailsToCancelOwnerEvent(context);
			}
			if (event != null) {
				context.setCalendarEvent(event);
			} else {
				context.setError(true);
				Validations.addErrorToContext("EVENT_DETAILS_EMPTY",
						"Unable to get event details SO NOT ABLE TO CANCLE calendar event", context);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in DAO layer");
		}

	}

	public RequestContext cancelCalendarEvent(RequestContext context, EventVO event) {

		String mailId = null;

		if (StringConstants.ApiConstants.CANCEL_OWNER_SLOT.equals(context.getApiName())
				|| StringConstants.ApiConstants.CANCEL_CALENDAR_EVENT.equals(context.getApiName())) {
			mailId = event.getOwnerMailId();
		}

		if (StringConstants.ApiConstants.CANCEL_USER_SLOT.equals(context.getApiName())
				|| StringConstants.ApiConstants.DENY_AND_REBOOK_SLOT.equals(context.getApiName())) {
			mailId = event.getUserMailId();
		}

		System.out.println("User who is cancelling the event *************************: " + mailId);
		String workingDirectory = System.getProperty("user.dir");

		String absoluteFilePath = workingDirectory + File.separator + CalendarUtils.CREDENTIALS_P12_FILE_NAME;

		if (mailId != null && event.getSlotMailId() != null) {

			Calendar service;
			try {
				service = CalendarUtils.getCalendarService(mailId, absoluteFilePath);
				cancelEvent(service, context, event,mailId);

			} catch (GeneralSecurityException | IOException e) {
				System.out.println("Exception occured in cancelling calendar event");
				e.printStackTrace();
			}

		} else {
			context.setError(true);
			Validations.addErrorToContext("userMailId", "sufficient fields are not there to push calendar event",
					context);
		}

		return context;
	}

	private void cancelEvent(Calendar service, RequestContext context, EventVO eventVo,String mailId) throws IOException {

		String eventId = getEventIdToCancel(service, eventVo.getFromDate(), eventVo.getToDate(), eventVo,mailId);

		if (eventId == null) {
			System.out.println("event id is null not going to cancel  ....");
			context.setError(true);
			Validations.addErrorToContext("event_id_not_found", "event not found to cancel", context);
		} else {
			System.out.println("got event id to cancel event :" + eventId);
			service.events().delete(CalendarUtils.CALENDAR_ID, eventId).setSendNotifications(true).execute();
			System.out.println("event has been canclled successfully :");
		}
	}

	private String getEventIdToCancel(Calendar service, String fromDate, String toDate, EventVO eventVO,String mailId)
			throws IOException {

		String eventIdToCancel = null;
		System.out.println("slot mail id :" + eventVO.getSlotMailId());
		System.out.println("to get events passing  mailid :" + mailId);

		Date javaFromDate = CalendarUtils.getJavaDate(fromDate);
		Date javaToDate = null;
		if (toDate != null && !toDate.isEmpty()) {
			javaToDate = CalendarUtils.getJavaDate(toDate);
		}
		System.out.println("from Date:" + fromDate);
		System.out.println("to Date:" + toDate);

		System.out.println("javaFrom Date:" + javaFromDate);
		System.out.println("javato Date:" + javaToDate);

		Events events;
		if (javaToDate != null) {
			events = service.events().list(mailId).setMaxResults(100)
					.setTimeMin(new DateTime(javaFromDate)).setTimeMax(new DateTime(javaToDate)).setOrderBy("startTime")
					.setSingleEvents(true).execute();
		} else {
			events = service.events().list(mailId).setMaxResults(100)
					.setTimeMin(new DateTime(javaFromDate)).setOrderBy("startTime").setSingleEvents(true).execute();
		}

		List<Event> items = events.getItems(); // TODO need to handle NPE

		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				// DateTime start = event.getStart().getDateTime();
				// if (start == null) {
				// start = event.getStart().getDate();
				// }
				System.out.println("Event Summury : " + event.getSummary());
				System.out.println("organizer :" + event.getOrganizer().getEmail());
				List<EventAttendee> list = event.getAttendees();
				if (list != null && list.size() > 0) {

					for (EventAttendee attendee : list) {
						if (eventVO.getSlotMailId().equals(attendee.getEmail())) {
							System.out.println("**** found slot mail id ****");
							return event.getId();
						}

					}
				}
			}
		}
		return eventIdToCancel;
	}

}
