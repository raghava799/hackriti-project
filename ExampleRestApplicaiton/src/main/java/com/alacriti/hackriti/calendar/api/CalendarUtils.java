package com.alacriti.hackriti.calendar.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alacriti.hackriti.factory.ResourceFactory;

public class CalendarUtils {

	public static Date getJavaDate(String eventDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(eventDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getNextDay(String oldDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	public static String getResourceMailId(String slotNumber) {
		return ResourceFactory.getResourceEMails().get(slotNumber);
	}

}
