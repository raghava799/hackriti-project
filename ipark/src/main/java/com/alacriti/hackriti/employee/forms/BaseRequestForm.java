package com.alacriti.hackriti.employee.forms;

import com.alacriti.hackriti.context.RequestContext;

public interface BaseRequestForm {

	public RequestContext validate(RequestContext context);

}
