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
import com.alacriti.hackriti.vo.EventVO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

public class CreateCalendarEventApiHandler implements BaseApiHandler {

	private static final String EVENT_SUMMURY = "Parking-Slot-Booking";

	private static final String EVENT_LOCATION = "Hyderabad";

	private static final String EVENT_DESCRIPTION = "Booking-slot";

	private static final String CALENDAR_ID = "primary";

	private static final String APPLICATION_NAME = "ParkingSlotManagement";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	// private static final String SERVICE_ACCOUNT_USER = "asha@alacriti.co.in";
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_P12_FILE_NAME = "credentials.p12";

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {

		getEventDetails(context);

		if (!context.isError()) {

			System.out.println("going to push calendar event ....");
			createCalendarEvent(context, context.getCalendarEvent());
		} else {
			return;
		}
	}

	public void getEventDetails(RequestContext context) throws BOException {

		CalendarDAO dao = new CalendarDAO();

		try {

			EventVO event = dao.getEventDetails(context);
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

	public RequestContext createCalendarEvent(RequestContext context, EventVO event)
			throws GeneralSecurityException, IOException {

		String workingDirectory = System.getProperty("user.dir");

		String absoluteFilePath = workingDirectory + File.separator + CREDENTIALS_P12_FILE_NAME;

		NetHttpTransport HTTP_TRANSPORT = null;

		if (event.getUserMailId() != null || event.getOwnerMailId() != null || event.getSlotMailId() != null) {

			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

			Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					getCredentials(HTTP_TRANSPORT, absoluteFilePath, event.getUserMailId()))
							.setApplicationName(APPLICATION_NAME).build();

			createEvent(service, context, event);

		} else {
			context.setError(true);
			Validations.addErrorToContext("userMailId", "sufficient fields are not there to push calendar event",
					context);
		}

		return context;
	}

	private void createEvent(Calendar service, RequestContext context, EventVO eventVo) throws IOException {

		System.out.println("eventVo.getFloor()" + eventVo.getFloor());
		System.out.println("eventVo.getFromDate()" + eventVo.getFromDate());
		System.out.println("eventVo.getOwnerMailId()" + eventVo.getOwnerMailId());
		System.out.println("eventVo.getParkingType()" + eventVo.getParkingType());
		System.out.println("eventVo.getSlotMailId()" + eventVo.getSlotMailId());
		System.out.println("eventVo.getSlotNumber()" + eventVo.getSlotNumber());
		System.out.println("eventVo.getToDate()" + eventVo.getToDate());
		System.out.println("eventVo.getUserMailId()" + eventVo.getUserMailId());

		String eventDate = eventVo.getFromDate();
		String nextDay = CalendarUtils.getNextDay(eventDate);
		Date eventStartDate = CalendarUtils.getJavaDate(eventDate);
		Date eventEndDate = CalendarUtils.getJavaDate(nextDay);

		Event event = new Event().setSummary(EVENT_SUMMURY).setLocation(EVENT_LOCATION)
				.setDescription(EVENT_DESCRIPTION);

		event.setStart(new EventDateTime().setDateTime(new DateTime(eventStartDate)));

		event.setEnd(new EventDateTime().setDateTime(new DateTime(eventEndDate)));

		// String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
		// event.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] { new EventAttendee().setEmail(eventVo.getOwnerMailId()),
				new EventAttendee().setEmail(eventVo.getSlotMailId()), };
		event.setAttendees(Arrays.asList(attendees));

		// EventReminder[] reminderOverrides = new EventReminder[] {
		// new EventReminder().setMethod("email").setMinutes(24 * 60),
		// new EventReminder().setMethod("popup").setMinutes(10), };
		// Event.Reminders reminders = new
		// Event.Reminders().setUseDefault(false)
		// .setOverrides(Arrays.asList(reminderOverrides));
		// event.setReminders(reminders);

		event = service.events().insert(CALENDAR_ID, event).setSendNotifications(true).execute();
		System.out.printf("Event created: %s\n", event.getHtmlLink());
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String absoluteFilePath, String sender)
			throws GeneralSecurityException, IOException {

		return new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT).setServiceAccountPrivateKeyFromP12File(new File(absoluteFilePath))
				.setServiceAccountScopes(SCOPES).setServiceAccountUser(sender).build();

	}

}
