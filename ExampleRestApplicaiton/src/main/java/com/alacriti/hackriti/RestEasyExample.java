package com.alacriti.hackriti;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
 
@Path("/publish")
public class RestEasyExample {
 
	final static Logger logger = Logger.getLogger(RestEasyExample.class);

    @GET
    @Path("/{message}")
    public Response publishMessage(@PathParam("message") String msgStr){
    	logger.info("log4j is initialized ....!!!");
        String responseStr = "Received message: "+msgStr;
        return Response.status(200).entity(responseStr).build();
    }
}
