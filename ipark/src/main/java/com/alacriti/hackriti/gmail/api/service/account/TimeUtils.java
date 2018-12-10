package com.alacriti.hackriti.gmail.api.service.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	protected static Calendar getBeforeTime(Date epochDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(epochDate);
		calendar.add(Calendar.MINUTE, -30);
		return calendar;
	}

	protected static long getEpochTime(Calendar calendar) {
		Date result = calendar.getTime();
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

		String beforeTime = crunchifyFormat.format(result);
		System.out.println("Time = " + beforeTime);

		Date date = null;
		try {
			date = crunchifyFormat.parse(beforeTime);
		} catch (ParseException e) {
			System.out.println("got ParseException");
			e.printStackTrace();
		}
		return date.getTime();
	}

}
