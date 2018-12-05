package com.alacriti.hackriti.calendar.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

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
import com.google.api.services.calendar.model.Events;

public class CalendarAccessor {
	private static final String APPLICATION_NAME = "ParkingSlotManagement";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/**
	 * Global instance of the scopes required by this quickstart. If modifying
	 * these scopes, delete your previously saved tokens/ folder.
	 */

	private static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_USER = "asha@alacriti.co.in";
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_P12_FILE_NAME = "/src/main/resources/credentials.p12";

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT
	 *            The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException
	 *             If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String absoluteFilePath)
			throws GeneralSecurityException, IOException {

		return new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT).setServiceAccountPrivateKeyFromP12File(new File(absoluteFilePath))
				.setServiceAccountScopes(SCOPES).setServiceAccountUser(SERVICE_ACCOUNT_USER).build();

	}

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		String absoluteFilePath = "";

		String workingDirectory = System.getProperty("user.dir");

		absoluteFilePath = workingDirectory + File.separator + CREDENTIALS_P12_FILE_NAME;

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, absoluteFilePath)).setApplicationName(APPLICATION_NAME).build();

		// List the next 10 events from the primary calendar.
		// getEvents(service);
		// createEvent(service);
		cancelEvent(service);
	}

	private static void cancelEvent(Calendar service) {

		// Delete an event
		try {
			service.events().delete("primary", "14i13j5jfifagurn6gojkjb8u8").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("event has been canclled successfully :");

	}

	private static void createEvent(Calendar service) {

		Event event = new Event().setSummary("SlotBooking").setLocation("Hyderabad").setDescription("Booking slottt");

		DateTime startDateTime = new DateTime("2018-12-15T09:00:00-07:00");
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("America/Los_Angeles");
		event.setStart(start);

		DateTime endDateTime = new DateTime("2018-12-20T17:00:00-07:00");
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Los_Angeles");
		event.setEnd(end);

		// String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
		// event.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] { new EventAttendee().setEmail("raghava@alacriti.co.in"),
				new EventAttendee().setEmail("alacriti.co.in_3937343532363838393230@resource.calendar.google.com"), };
		event.setAttendees(Arrays.asList(attendees));

		// EventReminder[] reminderOverrides = new EventReminder[] {
		// new EventReminder().setMethod("email").setMinutes(24 * 60),
		// new EventReminder().setMethod("popup").setMinutes(10), };
		// Event.Reminders reminders = new
		// Event.Reminders().setUseDefault(false)
		// .setOverrides(Arrays.asList(reminderOverrides));
		// event.setReminders(reminders);

		String calendarId = "primary";
		try {
			event = service.events().insert(calendarId, event).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Event created: %s\n", event.getHtmlLink());
	}

	private static void getEvents(Calendar service) throws IOException {
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list(SERVICE_ACCOUNT_USER).setMaxResults(10).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				System.out.println("event id :" + event.getId());
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
				System.out.println("organizer :" + event.getOrganizer().getDisplayName());
				List<EventAttendee> lists = event.getAttendees();
				for (EventAttendee attendee : lists) {
					System.out.println("attendee" + attendee.getDisplayName());
				}
			}
		}
	}
}
