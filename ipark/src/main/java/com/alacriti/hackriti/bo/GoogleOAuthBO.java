package com.alacriti.hackriti.bo;


import com.alacriti.hackriti.resource.handlers.GoogleAuthorizationCodeTokenV4Request;
import com.alacriti.hackriti.utils.constants.EnvVarConfig;
import com.alacriti.hackriti.vo.UserTokenData;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.*;
import com.google.api.client.json.jackson2.*;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleOAuthBO {

	public static UserTokenData tokenVerifer(String idTokenString) throws IOException {

		UserTokenData userTokenData = new UserTokenData();
		GoogleIdToken idToken = null;
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList(EnvVarConfig.getOauthClientId())).build();


		try {

			idToken = verifier.verify(idTokenString);
//			idToken = GoogleIdToken.parse( new JacksonFactory(), idTokenString);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			//ExceptionConstants error_code = ExceptionConstants.BO_GOOGLE_ERROR;
			// throw new ModelException("AdminUser service error", error_code);
//			throw new Exception("AdminUser service error");
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		if (idToken != null) {
			GoogleIdToken.Payload payload = idToken.getPayload();
			// Print user identifier
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);

			String email = payload.getEmail();
			String name = (String) payload.get("name");

         boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
         String pictureUrl = (String) payload.get("picture");
         String locale = (String) payload.get("locale");
         String familyName = (String) payload.get("family_name");
         String givenName = (String) payload.get("given_name");

			System.out.println("name :: " + name);
			System.out.println("email :: " + email);


			System.out.println("JwtId :: " + payload.getJwtId());
			System.out.println("emailVerified :: " + emailVerified);
			System.out.println("pictureUrl :: " + pictureUrl);
			System.out.println("locale :: " + locale);
			System.out.println("familyName :: " + familyName);
			System.out.println("givenName :: " + givenName);
			System.out.println("extradata :: " + payload.getJwtId());


			userTokenData.setEmailId(email);


			userTokenData.setName(name);
			userTokenData.setUserName(givenName);
			userTokenData.setEmailId(email);
			userTokenData.setClientKey(pictureUrl);
			userTokenData.setRole(familyName);
			userTokenData.setPictureUrl(pictureUrl	);
		} else {
			System.out.println("Invalid ID token.");
		}

		return userTokenData;
	}

	public static String requestAccessToken(String code) throws IOException {
		String idToken = null;
		try {
			System.out.println("code: " + code);
			System.out.println("Token Server url: " + EnvVarConfig.getOauthTokenServerEncodedUrl());
			System.out.println("Client Id: " + EnvVarConfig.getOauthClientId());
			System.out.println("Client Secret : " + EnvVarConfig.getOauthClientSecret());
			System.out.println("redirect uri: " + EnvVarConfig.getOauthRedirectUri());
			GoogleTokenResponse response =
					new GoogleAuthorizationCodeTokenV4Request(new NetHttpTransport(), new JacksonFactory(),
							EnvVarConfig.getOauthClientId(),
							EnvVarConfig.getOauthClientSecret(),
							code, EnvVarConfig.getOauthRedirectUri())
							.execute();
			System.out.println("Access token: " + response.getAccessToken());
			System.out.println("Id token: " + response.getIdToken());
			System.out.println("Refresh token: " + response.getRefreshToken());
			System.out.println("scope: " + response.getScope());
			System.out.println("Expires: " + response.getExpiresInSeconds());
			System.out.println("Unknown Keys: " + response.getUnknownKeys());
			idToken = response.getIdToken();


		} catch (TokenResponseException e) {
			if (e.getDetails() != null) {
				System.err.println("Error: " + e.getDetails().getError());
				if (e.getDetails().getErrorDescription() != null) {
					System.err.println(e.getDetails().getErrorDescription());
				}
				if (e.getDetails().getErrorUri() != null) {
					System.err.println(e.getDetails().getErrorUri());
				}

			} else {
				System.err.println(e.getMessage());
			}
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return idToken;
	}

}
