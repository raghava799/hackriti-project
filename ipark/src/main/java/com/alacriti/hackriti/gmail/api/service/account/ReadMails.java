package com.alacriti.hackriti.gmail.api.service.account;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alacriti.hackriti.dao.BaseDAO;
import com.alacriti.hackriti.dao.EmployeeDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.DateConverterUtils;
import com.alacriti.hackriti.vo.EmployeeParkingVO;
import com.alacriti.hackriti.vo.EventVO;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

public class ReadMails {

	private static final String APPLICATION_NAME = "ParkingSlotManagement";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String MAX_RESULTS = "10";
	private static final String LABEL_INBOX = "INBOX";
	private static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_USER = "atchyutakiran@alacriti.co.in";
	private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_READONLY);
	private static final String CREDENTIALS_P12_FILE_NAME = "/src/main/resources/credentials.p12";

	private static long currentEpochTime;
	private static long beforeEpochTime;

	private static List<String> leaveKeyWords = new ArrayList<String>();
	private static List<String> weekday = new ArrayList<String>();
	private static Map<String, Integer> dayMap = new HashMap();
	private static List<String> dateRegexPatterns = new ArrayList<String>();
	private static Map<String, Integer> patternLengthMap = new HashMap<String, Integer>();
	private static Map<String, String> datePattern = new HashMap<String, String>();
	static {
		leaveKeyWords.add("Comp");
		leaveKeyWords.add("Leave");
		leaveKeyWords.add("WFH");
		leaveKeyWords.add("Work From Home");
		leaveKeyWords.add("Off");

		weekday.add("sunday");
		weekday.add("monday");
		weekday.add("tuesday");
		weekday.add("wednesday");
		weekday.add("thursday");
		weekday.add("friday");
		weekday.add("saturday");

		dayMap.put("sunday", 1);
		dayMap.put("monday", 2);
		dayMap.put("tuesday", 3);
		dayMap.put("wednesday", 4);
		dayMap.put("thursday", 5);
		dayMap.put("friday", 6);
		dayMap.put("saturday", 7);

		dateRegexPatterns.add("[0-9]{2}/[0-9]{2}/[0-9]{4}");
		dateRegexPatterns.add("[0-9]{2}-[0-9]{2}-[0-9]{4}");
		dateRegexPatterns.add("[0-9]{4}/[0-9]{2}/[0-9]{2}");
		dateRegexPatterns.add("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		dateRegexPatterns.add("[0-9]{1,2} [a-zA-Z]{3} [0-9]{4}");
		dateRegexPatterns.add("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3} [0-9]{4}");
		dateRegexPatterns.add("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3}");
		dateRegexPatterns.add("[0-9]{1,2} [a-zA-Z]{3}");
		

		patternLengthMap.put("[0-9]{2}/[0-9]{2}/[0-9]{4}", 10);
		patternLengthMap.put("[0-9]{2}-[0-9]{2}-[0-9]{4}", 10);
		patternLengthMap.put("[0-9]{4}/[0-9]{2}/[0-9]{2}", 10);
		patternLengthMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}", 10);
		patternLengthMap.put("[0-9]{1,2} [a-zA-Z]{3} [0-9]{4}", 11);
		patternLengthMap.put("[0-9]{1,2} [a-zA-Z]{3}", 6);
		patternLengthMap.put("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3} [0-9]{4}", 13);
		patternLengthMap.put("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3}", 8);
		
		datePattern.put("[0-9]{2}/[0-9]{2}/[0-9]{4}", "dd/MM/yyyy");
		datePattern.put("[0-9]{2}-[0-9]{2}-[0-9]{4}", "dd-MM-yyyy");
		datePattern.put("[0-9]{4}/[0-9]{2}/[0-9]{2}", "yyyy/MM/dd");
		datePattern.put("[0-9]{4}-[0-9]{2}-[0-9]{2}", "yyyy-MM-dd");
		datePattern.put("[0-9]{1,2} [a-zA-Z]{3} [0-9]{4}", "dd MMM yyyy");
		datePattern.put("[0-9]{1,2} [a-zA-Z]{3}", "dd MMM");
		datePattern.put("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3} [0-9]{4}", "dd MMM yyyy");
		datePattern.put("[0-9]{1,2}[a-z]{2} [a-zA-Z]{3}", "dd MMM");
	}

	public static void main(String[] args)
			throws IOException, GeneralSecurityException, BOException, ParseException, SQLException {


		String absoluteFilePath = "";

		String workingDirectory = System.getProperty("user.dir");

		absoluteFilePath = workingDirectory + File.separator + CREDENTIALS_P12_FILE_NAME;

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, absoluteFilePath)).setApplicationName(APPLICATION_NAME).build();

		List<Message> messages = getMessagesWithLabels(service, SERVICE_ACCOUNT_USER, Arrays.asList(LABEL_INBOX));

		if (messages != null && messages.size() > 0) {

			for (Message message : messages) {

				message = readMessage(service, SERVICE_ACCOUNT_USER, message.getId());

				if (message != null && message.getPayload() != null && message.getPayload().getHeaders().size() > 0) {
					System.out.println("snippet :" + message.getSnippet());
					System.out.println(message.decodeRaw());
					String sentFrom = "";
					String messageSubject = "";
					String sentOn = "";
					String mailContent = message.getSnippet();
					boolean isLeaveMail = false;
					for (int i = 0; i < message.getPayload().getHeaders().size(); i++) {

						System.out.println();
						if ("From".equalsIgnoreCase(message.getPayload().getHeaders().get(i).getName())) {
							sentFrom = message.getPayload().getHeaders().get(i).getValue();
							System.out.println(sentFrom);
							if (sentFrom.contains("<") && sentFrom.contains(">"))
								sentFrom = sentFrom.substring(sentFrom.indexOf('<') + 1, sentFrom.indexOf('>'));
							System.out.println("Message Sent By: " + sentFrom);
						}
						if ("Subject".equalsIgnoreCase(message.getPayload().getHeaders().get(i).getName())) {
							messageSubject = message.getPayload().getHeaders().get(i).getValue();
							isLeaveMail = isLeaveMail(messageSubject);
							System.out.println("Message Subject: " + messageSubject);
						}
						if ("Date".equalsIgnoreCase(message.getPayload().getHeaders().get(i).getName())) {
							sentOn = message.getPayload().getHeaders().get(i).getValue();
							System.out.println("Message Sent On" + sentOn);
						}
					}

					System.out.println("Is Leave Mail: " + isLeaveMail);
					System.out.println("Mail Content: " + mailContent);
					if (isLeaveMail) {

						EmployeeDAO dao = new EmployeeDAO();
						Connection conn = null;
						try {
							conn = BaseDAO.getConnection();
							// DBInitializer dbInit = new DBInitializer();
							// Connection conn = dbInit.initializeDataSource();
							// ParkingDAO dao = new ParkingDAO();
							dao.setConnection(conn);
							EmployeeParkingVO vo = dao.getEmployeeParkingSlotDetails(sentFrom);
							if (vo == null) {
								System.out.println("The user does not have a parking slot" + "\n");
							} else {
								System.out.println(
										sentFrom + " is having parking slot " + vo.getEmpParkingSlotId() + "\n");
								ArrayList<Date> leaveList = extractLeaveDates(mailContent);
								System.out.println("Leave Size: "+leaveList.size());
								int updatedCount = dao.insertLeaveDates(vo, leaveList, messageSubject);
								if (updatedCount>0)
								{
									int leaveCount = (int) ((leaveList.get(1).getTime() - leaveList.get(0).getTime())/ (1000*60*60*24)) + 1;
									System.out.println(leaveCount);
									dao.insertLeaveDataForParking(vo, leaveList.get(0), leaveCount);
								}
								EventVO eventVO = new EventVO();
								
							}
						}
						catch(Exception e)
						{
							if (conn!=null)
							{
								System.out.println("rollback connection");
								conn.rollback();
							}
						}
						finally{
							if (conn!=null)
							{
								System.out.println("Committing connection");
								conn.commit();
							}
						}
					}
				}
			}
		}

	}

	private static ArrayList<Date> extractLeaveDates(String mailContent) throws ParseException {

		int leaveDayOfWeek = isLeaveMailByDay(mailContent);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calendar = Calendar.getInstance();
		Date leaveStartDate = calendar.getTime();
			
		Date leaveEndDate = calendar.getTime();
		ArrayList<Date> dateList = new ArrayList<Date>();

		int noOfDaysLeave = 0;

		System.out.println("Calander Date: " + calendar);
		System.out.println("Calander Date: " + calendar.getTime());
		

		if (leaveDayOfWeek > 0) 
		{
			leaveStartDate = getLeaveDate(leaveDayOfWeek, calendar);
			leaveEndDate = leaveStartDate;
			noOfDaysLeave = leaveEndDate.getDate() - leaveStartDate.getDate() + 1;
			System.out.println(noOfDaysLeave);
			dateList.add(leaveStartDate);
			dateList.add(leaveEndDate);
		} else {
			System.out.println("Regex patterns");
			boolean patternMatched = false;
			if (!patternMatched) 
			{
				for (String regex : dateRegexPatterns) 
				{
					System.out.println("Regex: " + regex);
					if (mailContent.matches(".*" + regex + ".*") && !patternMatched) 
					{
						patternMatched = true;
						System.out.println("Pattern Matched");
						dateList = extractDatePattern(mailContent, regex);
					}
				}
			}
			if (!patternMatched)
			{
				if (mailContent.toLowerCase().contains("today") && mailContent.toLowerCase().contains("tomorrow"))
				{
					dateList.add(leaveStartDate);
					calendar.add(Calendar.DATE, 1);
					leaveEndDate = calendar.getTime();
				}
				else if (mailContent.toLowerCase().contains("tomorrow"))
				{	
					calendar.add(Calendar.DATE, 1);
					leaveStartDate = calendar.getTime();
					leaveEndDate = leaveStartDate;
					dateList.add(leaveStartDate);
					dateList.add(leaveEndDate);
				}
				else 
				{
					System.out.println("Adding leave "+leaveStartDate+" : "+leaveEndDate);
					dateList.add(leaveStartDate);
					dateList.add(leaveEndDate);
					System.out.println("List Size: "+dateList.size());
				}
			}
			
		}
		
		return dateList;
	}

	private static ArrayList<Date> extractDatePattern(String mailContent, String regex) 
	{
		ArrayList<Date> dateList = new ArrayList<Date>();
		int patternLength = patternLengthMap.get(regex);
		String dateFormat = datePattern.get(regex);
		boolean updateDateFormat = false;
		if (dateFormat.equalsIgnoreCase("dd MMM"))
		{
			// Need to add year to date and update format.
			dateFormat = dateFormat+" yyyy";
			updateDateFormat = true;
		}
		StringBuilder sb = new StringBuilder("");
		int indexChange = 0;
		int endIndex = mailContent.length();
		for (int i = 0; i < patternLength - 1; i++) {
			sb.append("*");
		}
		String str1 = mailContent.replaceFirst(regex, sb.toString() + 1);
		System.out.println("*** STR1: " + str1);
		if (str1.indexOf(sb.toString() + 1) + patternLength > mailContent.length())
			endIndex = str1.indexOf(sb.toString() + 1) + patternLength - 1;
		else
			endIndex = str1.indexOf(sb.toString() + 1) + patternLength;

		String date1 = mailContent.substring(str1.indexOf(sb.toString() + 1), str1.indexOf(sb.toString() + 1) + patternLength).trim();
		if (updateDateFormat)
		{
			Calendar calendar = Calendar.getInstance();
			date1=date1+" "+calendar.get(Calendar.YEAR);
		}
		
		if (date1.length() < patternLength) {
			indexChange = 1;
			System.out.println(indexChange);
		}
		date1 =  date1.replaceAll("th ", " ");
		System.out.println("Date ONE: "+date1);
		date1=date1.replace(",", "");
		date1=date1.replace(".", "");
		
		System.out.println("Date 1: " + date1);
		DateConverterUtils utils = new DateConverterUtils();
		dateList.add(utils.dateFormater(date1, dateFormat));
		
		System.out.println("str 1: " + str1);
		if (str1.matches(".*" + regex + ".*")) {
			System.out.println("Matched again");
			str1 = str1.replaceFirst(regex, sb.toString() + 2);
			System.out.println(str1);
			if (str1.indexOf(sb.toString() + 2) + patternLength - indexChange > mailContent.length())
				endIndex = mailContent.length();
			else
				endIndex = str1.indexOf(sb.toString() + 2) + patternLength - indexChange;
			String date2 = mailContent.substring(str1.indexOf(sb.toString() + 2) - indexChange,
					str1.indexOf(sb.toString() + 2) + patternLength - indexChange).trim().replaceAll("th ", " ");
			date2=date2.replace(",", "");
			date2=date2.replace(".", "");
			if (updateDateFormat)
			{
				Calendar calendar = Calendar.getInstance();
				date2=date2+" "+calendar.get(Calendar.YEAR);
			}
			System.out.println("Date 2: " + date2);
			dateList.add(utils.dateFormater(date2, dateFormat));
		}
		else 
		{
			dateList.add(utils.dateFormater(date1, dateFormat));
		}
		return dateList;
	}

	private static Date getLeaveDate(int leaveDayOfWeek, Calendar calendar) {
		Calendar leaveDate = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek - leaveDayOfWeek == 0) {
			leaveDate.add(Calendar.DATE, 7);
		} else if (dayOfWeek - leaveDayOfWeek > 0) {
			leaveDate.add(Calendar.DATE, (7 - dayOfWeek + leaveDayOfWeek));
		} else if (dayOfWeek - leaveDayOfWeek < 0) {
			leaveDate.add(Calendar.DATE, (leaveDayOfWeek - dayOfWeek));
		}
		System.out.println(leaveDate.getTime());
		// String leaveDate = dateFormat.format(leaveDate1.getTime());

		return leaveDate.getTime();
	}

	private static Integer isLeaveMailByDay(String messageContent) {
		for (String day : weekday) {
			if (messageContent.toLowerCase().contains(day)) {
				return dayMap.get(day);
			}
		}
		return -1;
	}

	private static boolean isLeaveMail(String messageSubject) {
		for (String leave : leaveKeyWords)
			if (messageSubject.toLowerCase().contains(leave.toLowerCase()))
				return true;
		return false;
	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String absoluteFilePath)
			throws GeneralSecurityException, IOException {

		return new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT).setServiceAccountPrivateKeyFromP12File(new File(absoluteFilePath))
				.setServiceAccountScopes(SCOPES).setServiceAccountUser(SERVICE_ACCOUNT_USER).build();

	}

	public static Message readMessage(Gmail service, String userId, String messageId) throws IOException {

		return service.users().messages().get(userId, messageId).execute();
	}

	public static List<Message> getMessagesWithLabels(Gmail service, String userId, List<String> labelIds)
			throws IOException {

		// in:sent
		// before : yyyy/mm/dd indicates exclusive of the date mentioned
		// after : yyyy/mm/dd indicates inclusive of the date mentioned
		// newer_than:1d d (day), m (month), and y (year)

		// we can use Epoch time for after and before keys
		getTimeIntervalsInEpochTime();

		ListMessagesResponse response = service.users().messages().list(userId).setLabelIds(labelIds)
				.setMaxResults(Long.parseLong(MAX_RESULTS)).execute();

		List<Message> messages = null;

		if (response != null && response.getMessages() != null && response.getMessages().size() > 0) {
			messages = new ArrayList<Message>();
			messages.addAll(response.getMessages());
		} else {
			System.out.println("No Message Found ....!");
		}

		return messages;
	}

	public static void getTimeIntervalsInEpochTime() {
		Calendar calendar = Calendar.getInstance();
		Date today = Calendar.getInstance().getTime();
		currentEpochTime = TimeUtils.getEpochTime(calendar);
		System.out.println("currentEpochTime:" + currentEpochTime);
		beforeEpochTime = TimeUtils.getEpochTime(TimeUtils.getBeforeTime(today));
		System.out.println("before:" + beforeEpochTime);

	}

	/*
	 * public static getFilter(){
	 * 
	 * Filter filter = new Filter() .setCriteria(new FilterCriteria()
	 * .setFrom("cat-enthusiasts@example.com")) .setAction(new FilterAction()
	 * .setAddLabelIds(Arrays.asList(labelId))
	 * .setRemoveLabelIds(Arrays.asList("UNREAD"))); }
	 */
}
