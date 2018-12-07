package com.alacriti.hackriti.resource.handlers;

import com.alacriti.hackriti.bo.GoogleOAuthBO;
import com.alacriti.hackriti.resource.handlers.jwtHandler.JWThandler;
import com.alacriti.hackriti.utils.constants.RestConstants;
import com.alacriti.hackriti.vo.UserTokenData;

import javax.ws.rs.core.*;
import java.io.*;

public class OauthHandler {

	public Response login(String code) {
		Response response = Response.status(Response.Status.FORBIDDEN).build();
		try {
			String idToken;
			UserTokenData userTokenData;
			idToken = GoogleOAuthBO.requestAccessToken(code);
			if (idToken != null) {
				userTokenData = GoogleOAuthBO.tokenVerifer(idToken);
				System.out.println("oauth success");
				if (userTokenData.getEmailId() != null) {
					JWThandler jwThandler = new JWThandler();
					String token = jwThandler.newToken(userTokenData);
					response = Response.ok().header(RestConstants.UrlConstants.AUTH_HEADER, token).entity(userTokenData).build();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return response;
	}
}
