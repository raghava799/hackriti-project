package com.alacriti.hackriti.resource.handlers;


import com.alacriti.hackriti.resource.handlers.jwtHandler.JWThandler;
import com.alacriti.hackriti.utils.constants.EnvVarConfig;
import com.alacriti.hackriti.utils.constants.RestConstants;
import com.alacriti.hackriti.vo.UserTokenData;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path(RestConstants.UrlConstants.OAUTH)
public class OauthResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMySelf(@HeaderParam(RestConstants.UrlConstants.AUTH_HEADER) String token) {
		JWThandler jwThandler = new JWThandler();
		UserTokenData userTokenData = jwThandler.getUserData(token);
		return Response.ok().entity(userTokenData).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(RestConstants.UrlConstants.LOGIN)
	public Response login(@QueryParam(RestConstants.UrlConstants.QUERY_PARAM_TOKEN_CODE) String code) {
		OauthHandler oauthHandler = new OauthHandler();
		return  oauthHandler.login(code);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(RestConstants.UrlConstants.REFRESH_TOKEN)
	public Response refreshToken(@HeaderParam(RestConstants.UrlConstants.AUTH_HEADER) String token){
		Response response;
		JWThandler jwThandler = new JWThandler();
		String newToken = jwThandler.renewToken(token);
		HashMap<String, String> message = new HashMap<String, String>();
		if(newToken != null) {
			message.put("msg", "New Token Sent");
			response =  Response.ok().header(RestConstants.UrlConstants.AUTH_HEADER, newToken).entity(message).build();
		} else {
			message.put("error", "Time Out");
			response = Response.status(408).entity(message).build();
		}
		return response;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(RestConstants.UrlConstants.LOGIN_REDIRECT)
	public Response loginRedirect(@QueryParam (RestConstants.UrlConstants.QUERY_PARAM_TOKEN_CODE) String code) {
		String loginUrl = EnvVarConfig.getOauthUiLoginUrl() + "?" + RestConstants.UrlConstants.QUERY_PARAM_TOKEN_CODE + "=" + code;
		String html = getRedirectHtml(loginUrl);
		return  Response.ok().entity(html).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(RestConstants.UrlConstants.AUTH_PAGE)
	public Response AuthPageRedirect() {
		String loginUrl = EnvVarConfig.getOauthUiLoginUrl();
		String html = getRedirectHtml(loginUrl);
		return  Response.ok().entity(html).build();
	}

	private String getRedirectHtml(String redirectPath) {
		StringBuilder body = new StringBuilder();
		body.append("<html>");
		body.append("<head><script>");
		body.append("  function redirectLauncher() {");
		body.append("    console.log(\"Triggering launch :\" + window.location.href);");
		body.append("    window.location.href = \"" + redirectPath + "\";");
		body.append("  }");
		body.append("</script></head>");
		body.append("<body>");
		body.append("Please click button if not redirected automatically.");
		body.append("<input type=\"button\" value=\"Launch App\" onclick=\"redirectLauncher()\">");
		body.append("<script>redirectLauncher();</script>);</body>");
		body.append("</html>");

		return body.toString();
	}

}
