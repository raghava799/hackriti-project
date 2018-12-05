package com.alacriti.hackriti.gmail.api.service.account;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class MailAccessor {

	private static final String APPLICATION_NAME = "ParkingSlotManagement";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String MAX_RESULTS = "10";
	private static final String LABEL_INBOX = "INBOX";
	private static final String SERVICE_ACCOUNT = "raghavaserviceaccount@elm-system.iam.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_USER = "raghava@alacriti.co.in";
	private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY);
	private static final String CREDENTIALS_P12_FILE_NAME = "/src/main/resources/credentials.p12";

	private static long currentEpochTime;
	private static long beforeEpochTime;

	public static void main(String[] args) throws IOException, GeneralSecurityException {

		String absoluteFilePath = "";

		String workingDirectory = System.getProperty("user.dir");

		absoluteFilePath = workingDirectory + File.separator + CREDENTIALS_P12_FILE_NAME;

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, absoluteFilePath)).setApplicationName(APPLICATION_NAME).build();

		List<Message> messages = getMessagesWithLabels(service, SERVICE_ACCOUNT_USER, Arrays.asList(LABEL_INBOX));

		if (messages != null && messages.size() > 0) {

			for (Message message : messages) {

				System.out.println(message.toPrettyString());
				message = readMessage(service, SERVICE_ACCOUNT_USER, message.getId());

				if (message != null && message.getPayload() != null && message.getPayload().getHeaders().size() > 0) {
					System.out.println("snippet :" + message.getSnippet() + "\n");
				}
			}
		}

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

		ListMessagesResponse response = service.users().messages().list(userId)
				.setQ("after:" + beforeEpochTime + " " + "before:" + currentEpochTime).setLabelIds(labelIds)
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
