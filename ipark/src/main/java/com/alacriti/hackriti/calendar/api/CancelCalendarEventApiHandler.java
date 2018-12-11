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

		getEventDetails(context);

		if (!context.isError()) {

			System.out.println("going to cancel calendar event ....");
			cancelCalendarEvent(context, context.getCalendarEvent());
		} else {
			return;
		}
	}

	public void getEventDetails(RequestContext context) throws BOException {

		CalendarDAO dao = new CalendarDAO();

		try {

			EventVO event = null;
			if (StringConstants.ApiConstants.CANCEL_USER_SLOT.equals(context.getApiName())) {
				event = dao.getEventDetails(context);
			}

			if (StringConstants.ApiConstants.CANCEL_OWNER_SLOT.equals(context.getApiName())) {
				event = dao.getEventDetailsToCancelOwnerEvent(context);
			}
			if (event != null) {
				context.setCalendarEvent(event);
			} else {
				context.setError(true);
				Validations.addErrorToContext("", "Unable to get event details to push calendar event", context);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in DAO layer");
		}

	}

	public RequestContext cancelCalendarEvent(RequestContext context, EventVO event)
			throws GeneralSecurityException, IOException {
		
		String mailId = null;

		if (StringConstants.ApiConstants.CANCEL_OWNER_SLOT.equals(context.getApiName())) {
			mailId = event.getOwnerMailId();
		}

		if (StringConstants.ApiConstants.CANCEL_USER_SLOT.equals(context.getApiName())) {
			mailId = event.getUserMailId();
		}

		String workingDirectory = System.getProperty("user.dir");

		String absoluteFilePath = workingDirectory + File.separator + CalendarUtils.CREDENTIALS_P12_FILE_NAME;


		if (mailId != null && event.getSlotMailId() != null) {

			Calendar service = CalendarUtils.getCalendarService(mailId, absoluteFilePath);

			cancelEvent(service, context, event);

		} else {
			context.setError(true);
			Validations.addErrorToContext("userMailId", "sufficient fields are not there to push calendar event",
					context);
		}

		return context;
	}

	

	private void cancelEvent(Calendar service, RequestContext context, EventVO eventVo) throws IOException {

		String eventId = getEventIdToCancel(service, eventVo.getFromDate(), eventVo);

		if (eventId == null) {
			context.setError(true);
			Validations.addErrorToContext("event_id_not_found", "event not found to cancel", context);
		} else {
			service.events().delete(CalendarUtils.CALENDAR_ID, eventId).setSendNotifications(true).execute();
			System.out.println("event has been canclled successfully :");
		}
	}

	private String getEventIdToCancel(Calendar service, String date, EventVO eventVO) throws IOException {

		String eventIdToCancel = null;

		Date javaDate = CalendarUtils.getJavaDate(date);

		Events events = service.events().list(eventVO.getOwnerMailId()).setMaxResults(100)
				.setTimeMin(new DateTime(javaDate)).setOrderBy("startTime").setSingleEvents(true).execute();

		List<Event> items = events.getItems();

		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("Event Summury : ", event.getSummary(), start);
				System.out.println("organizer :" + event.getOrganizer().getEmail());
				List<EventAttendee> lists = event.getAttendees();
				for (EventAttendee attendee : lists) {
					if (attendee.getEmail().equals(eventVO.getSlotMailId())) {
						
						System.out.println("**** found slot mail id ****");
						return event.getId();
					}
				}
			}
		}
		return eventIdToCancel;
	}

}
