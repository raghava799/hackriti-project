package com.alacriti.hackriti.calendar.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alacriti.hackriti.factory.ResourceFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

public class CalendarUtils {
	
	
	public static final String EVENT_SUMMURY = "Parking-Slot-Booking";

	public static final String EVENT_LOCATION = "Hyderabad";

	public static final String EVENT_DESCRIPTION = "Booking-slot";

	public static final String CALENDAR_ID = "primary";

	public static final String APPLICATION_NAME = "ParkingSlotManagement";
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	public static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	// private static final String SERVICE_ACCOUNT_USER = "asha@alacriti.co.in";
	public static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	public static final String CREDENTIALS_P12_FILE_NAME = "credentials.p12";
	
	public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String absoluteFilePath, String sender)
			throws GeneralSecurityException, IOException {

		return new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(CalendarUtils.JSON_FACTORY)
				.setServiceAccountId(CalendarUtils.SERVICE_ACCOUNT)
				.setServiceAccountPrivateKeyFromP12File(new File(absoluteFilePath))
				.setServiceAccountScopes(CalendarUtils.SCOPES).setServiceAccountUser(sender).build();

	}
	
	public static Calendar getCalendarService(String mailId, String absoluteFilePath)
			throws GeneralSecurityException, IOException {
		NetHttpTransport HTTP_TRANSPORT;
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, CalendarUtils.JSON_FACTORY,
				CalendarUtils.getCredentials(HTTP_TRANSPORT, absoluteFilePath, mailId))
						.setApplicationName(CalendarUtils.APPLICATION_NAME).build();
		return service;
	}


	public static Date getJavaDate(String eventDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = format.parse(eventDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getNextDay(String oldDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		java.util.Calendar c = java.util.Calendar.getInstance();
		try {
			c.setTime(sdf.parse(oldDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Incrementing the date by 1 day
		c.add(java.util.Calendar.DAY_OF_MONTH, 1);
		String newDate = sdf.format(c.getTime());

		return newDate;
	}

//	public static String getResourceMailId(String slotNumber) {
//		return ResourceFactory.getResourceEMails().get(slotNumber);
//	}

}
