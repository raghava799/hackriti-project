package com.alacriti.hackriti.gmail.api.user.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

public class MailAccessor {
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String MAX_RESULTS = "10";
	private static final String LABEL_INBOX = "INBOX";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying
	 * these scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();

		String user = "asha@alacriti.co.in";

		List<Message> messages = getMessagesWithLabels(service, user, Arrays.asList(LABEL_INBOX));

		if (messages != null && messages.size() > 0) {
			for (Message message : messages) {
				System.out.println(message.toPrettyString());
				message = readMessage(service, user, message.getId());
				if (message != null && message.getPayload() != null && message.getPayload().getHeaders().size() > 0) {
					System.out.println("snippet :" + message.getSnippet() + "\n");
/*					for (MessagePartHeader header : message.getPayload().getHeaders()) {
						System.out.println(header.getName() + ":" + header.getValue() + "\n");
					}
*/				}
			}
		}

	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT
	 *            The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException
	 *             If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = MailAccessor.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public static List<Message> getMessagesWithLabels(Gmail service, String userId, List<String> labelIds)
			throws IOException {

		ListMessagesResponse response = service.users().messages().list(userId).setLabelIds(labelIds)
				.setMaxResults(Long.parseLong(MAX_RESULTS)).execute();

		List<Message> messages = null;

		if (response != null && response.getMessages().size() > 0) {
			messages = new ArrayList<Message>();
			messages.addAll(response.getMessages());
		}

		return messages;
	}

	public static Message readMessage(Gmail service, String userId, String messageId) throws IOException {

		return service.users().messages().get(userId, messageId).execute();
	}

}