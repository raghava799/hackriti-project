package com.alacriti.hackriti.api.handlers;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.exceptions.BOException;

public interface BaseApiHandler {

	public void handleRequest(RequestContext context) throws BOException;
}
