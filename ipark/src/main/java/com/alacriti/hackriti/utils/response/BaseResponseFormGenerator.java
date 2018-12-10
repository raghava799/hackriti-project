package com.alacriti.hackriti.utils.response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.response.forms.BaseResponseForm;

public interface BaseResponseFormGenerator {

	public BaseResponseForm generateResponse(RequestContext context);
}
