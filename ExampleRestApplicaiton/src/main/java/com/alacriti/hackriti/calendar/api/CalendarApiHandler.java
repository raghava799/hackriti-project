package com.alacriti.hackriti.calendar.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alacriti.hackriti.context.RequestContext;
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

public class CalendarApiHandler {

	private static final String EVENT_SUMMURY = "SlotBooking";

	private static final String EVENT_LOCATION = "Hyderabad";

	private static final String EVENT_DESCRIPTION = "Booking slottt";

	private static final String APPLICATION_NAME = "ParkingSlotManagement";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	// private static final String SERVICE_ACCOUNT_USER = "asha@alacriti.co.in";
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_P12_FILE_NAME = "/src/main/resources/credentials.p12";

	private String absoluteFilePath = "";

	private String userMailId;

	public String getUserMailId() {
		return userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String absoluteFilePath)
			throws GeneralSecurityException, IOException {

		return new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT).setServiceAccountPrivateKeyFromP12File(new File(absoluteFilePath))
				.setServiceAccountScopes(SCOPES).setServiceAccountUser(this.getUserMailId()).build();

	}

	private void createEvent(Calendar service, RequestContext context, EventVO eventVo) {

		String calendarId = "primary";

		String eventDate = context.getSlot().getDate();
		String nextDay = CalendarUtils.getNextDay(eventDate);
		Date eventStartDate = CalendarUtils.getJavaDate(eventDate);
		Date eventEndDate = CalendarUtils.getJavaDate(nextDay);

		String ownerMailId = eventVo.getOwnerMailId();
		String slotMailId = CalendarUtils.getResourceMailId(eventVo.getSlotNumber());

		Event event = new Event().setSummary(EVENT_SUMMURY).setLocation(EVENT_LOCATION)
				.setDescription(EVENT_DESCRIPTION);

		event.setStart(new EventDateTime().setDateTime(new DateTime(eventStartDate)));

		event.setEnd(new EventDateTime().setDateTime(new DateTime(eventEndDate)));

		// String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
		// event.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] { new EventAttendee().setEmail(ownerMailId),
				new EventAttendee().setEmail(slotMailId), };
		event.setAttendees(Arrays.asList(attendees));

		// EventReminder[] reminderOverrides = new EventReminder[] {
		// new EventReminder().setMethod("email").setMinutes(24 * 60),
		// new EventReminder().setMethod("popup").setMinutes(10), };
		// Event.Reminders reminders = new
		// Event.Reminders().setUseDefault(false)
		// .setOverrides(Arrays.asList(reminderOverrides));
		// event.setReminders(reminders);

		try {
			event = service.events().insert(calendarId, event).setSendNotifications(true).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Event created: %s\n", event.getHtmlLink());
	}

	public RequestContext createCalendarEvent(RequestContext context, EventVO event) {

		String workingDirectory = System.getProperty("user.dir");

		absoluteFilePath = workingDirectory + File.separator + CREDENTIALS_P12_FILE_NAME;

		NetHttpTransport HTTP_TRANSPORT = null;

		if (event.getUserMailId() != null) {

			this.userMailId = event.getUserMailId();

			try {

				HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

				Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
						getCredentials(HTTP_TRANSPORT, absoluteFilePath)).setApplicationName(APPLICATION_NAME).build();

				createEvent(service, context, event);

			} catch (GeneralSecurityException | IOException e) {
				context.setError(true);
				e.printStackTrace();
			}
		} else {
			context.setError(true);
			Validations.addErrorToContext("userMailId is not there to push calendar event", context);
		}

		return context;
	}

}
