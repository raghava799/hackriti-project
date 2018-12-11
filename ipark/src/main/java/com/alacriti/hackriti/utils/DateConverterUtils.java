package com.alacriti.hackriti.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateConverterUtils {
	public static Map<String, Integer> monthToDigit = new HashMap<String, Integer>();
	static 
	{
		monthToDigit.put("jan", 1);
		monthToDigit.put("feb", 2);
		monthToDigit.put("mar", 3);
		monthToDigit.put("apr", 4);
		monthToDigit.put("may", 5);
		monthToDigit.put("jun", 6);
		monthToDigit.put("jul", 7);
		monthToDigit.put("aug", 8);
		monthToDigit.put("sep", 9);
		monthToDigit.put("oct", 10);
		monthToDigit.put("nov", 11);
		monthToDigit.put("dec", 12);
	}
	
	public Date dateFormater(String actualDate, String dateFormat) {
		Date date=null;
		try 
		{
			System.out.println("Actual Date: "+actualDate);
			date=new SimpleDateFormat(dateFormat).parse(actualDate);
			System.out.println("Formatted Date: "+date);
			System.out.println(date);
		} catch (ParseException e) {
			System.out.println("Unable to parse date");
		}  
		return date;
	}
	
}
