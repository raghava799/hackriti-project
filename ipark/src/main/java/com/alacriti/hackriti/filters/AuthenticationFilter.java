package com.alacriti.hackriti.filters;

import com.alacriti.hackriti.resource.handlers.jwtHandler.JWTconstants;
import com.alacriti.hackriti.resource.handlers.jwtHandler.JWThandler;
import com.alacriti.hackriti.utils.constants.RestConstants;

import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;




@Provider()
public class AuthenticationFilter implements ContainerRequestFilter
{

    @Context
    private HttpServletRequest request;

    public void filter(ContainerRequestContext requestContext)
            throws IOException, NotAllowedException, NotAuthorizedException
    {

        System.out.println("Security filter is running!!");
//        if (isAvailable(requestContext.getUriInfo(), getAllowedPaths())) return;
//
//        JWTconstants jwTconstants = new JWTconstants();
//        requestContext.getCookies();
//        List<String> authHeaders = requestContext.getHeaders().get(RestConstants.UrlConstants.AUTH_HEADER);
//        if (authHeaders != null && authHeaders.size() > 0) {
//            System.out.println("Varifying JWT ");
//            String token = authHeaders.get(0);
//            JWThandler jwThandler = new JWThandler();
//            if (jwThandler.varifyToken(token)) {
//                return;
//            }
//            System.out.println(" INVALID TOKEN: " + token);
//        }
//
//        Response unAuthorized = Response.status(Response.Status.UNAUTHORIZED).build();
//        requestContext.abortWith(unAuthorized);


    }

    private boolean isAvailable(UriInfo uriInfo, ArrayList<String> allowedPaths) {
        boolean shouldAllow = false;
        URI absoluteUri = uriInfo.getAbsolutePath();
        URI baseUri = uriInfo.getBaseUri();
        URI relativeUri = baseUri.relativize(absoluteUri);
        System.out.println("absoluteUri: " + absoluteUri + " , baseUri: " + baseUri + " , relativeUri: " + relativeUri);
        for (String path : allowedPaths) {
            if (relativeUri.getPath().equals(path)) {
                shouldAllow = true;
                break;
            }
        }
        return shouldAllow;
        //        return true;
    }

    private ArrayList<String> getAllowedPaths() {
        ArrayList<String> allowedPaths = new ArrayList<String>();
        
        allowedPaths.add( RestConstants.UrlConstants.OAUTH + RestConstants.UrlConstants.LOGIN);
        allowedPaths.add( RestConstants.UrlConstants.OAUTH + RestConstants.UrlConstants.REFRESH_TOKEN);
        allowedPaths.add( RestConstants.UrlConstants.OAUTH + RestConstants.UrlConstants.LOGIN_REDIRECT);
        allowedPaths.add( RestConstants.UrlConstants.OAUTH + RestConstants.UrlConstants.AUTH_PAGE);
        
        
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.GET_EMPLOYEE);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.GET_OWNER_SLOT);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.GET_USER_SLOT);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.BOOK_SLOT);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.CANCEL_OWNER_SLOT);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.CANCEL_USER_SLOT);
        allowedPaths.add( RestConstants.UrlConstants.PARKING + RestConstants.UrlConstants.AVAILABLE_SLOTS);


        return allowedPaths;
    }

}
