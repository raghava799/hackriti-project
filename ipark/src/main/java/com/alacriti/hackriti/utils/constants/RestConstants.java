package com.alacriti.hackriti.utils.constants;

/**
 * Created by camila on 7/12/18.
 */
public class RestConstants {
	public class UrlConstants {
		public static final String QUERY_PARAM_TOKEN_CODE = "code";
		public static final String AUTH_HEADER = "Authorization";

		public static final String OAUTH = "oauth";
		public static final String PARKING = "/parking";

		public static final String LOGIN = "/login";
		public static final String REFRESH_TOKEN = "/refreshToken";
		public static final String LOGIN_REDIRECT = "/login_redirect";
		public static final String AUTH_PAGE = "/auth_page";
		public static final String GET_EMPLOYEE = "/getemployee";
		public static final String GET_OWNER_SLOT = "/owner/slot";
		public static final String GET_USER_SLOT = "/user/slot";
		
		public static final String AVAILABLE_SLOTS = "/slots/available";

		public static final String BOOK_SLOT = "/book/slot";
		public static final String CANCEL_OWNER_SLOT = "/cancel/owner/slot";
		public static final String CANCEL_USER_SLOT = "/cancel/user/slot";



	}

	// HTTP Statuses
	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int AUTHORIZATION_FAILURE = 401;
	public static final int FORBIDDEN = 403;
	public static final int RESOURCE_NOT_FOUND = 404;
	public static final int BAD_REQUEST = 400;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int ERROR_DATA_BAD_REQUEST = 422;

	public static final int SYSTEM_ERROR = 500; // swapping 403 and 500 as we
												// are introducing this
	// lately

	// HTTP REQUESTS
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";

	public static final String ERROR_PATH_PARAMS_RESPONSE_CODE = "error_field";
	public static final String ERROR_REQUEST_PARAM_RESPONSE_CODE = "error_request_param";
	public static final String ERROR_QUERY_PARAMS_RESPONSE_CODE = "error_field";
	public static final String ERROR_HEADER_PARAMS_RESPONSE_CODE = "error_field";
	public static final String ERROR_PATH_PARAMS_RESPONSE_MSG = "The error returned if a mandatory field is missing value or if the value is invalid. This is accompanied by the field that caused the error.";
	public static final String ERROR_QUERY_PARAMS_RESPONSE_MSG = "The error returned if a mandatory field is missing value or if the value is invalid. This is accompanied by the field that caused the error.";
	public static final String ERROR_HEADER_PARAMS_RESPONSE_MSG = "The error returned if a mandatory field is missing value or if the value is invalid. This is accompanied by the field that caused the error.";
	public static final String ERROR_REQUEST_PARAM_RESPONSE_MSG = "No record found with given request parameters.";
	public static final String ERROR_TECH_DIFF_PAGE_RESPONSE_CODE = "error_tech_difficulties";
	public static final String ERROR_TECH_DIFF_PAGE_RESPONSE_MSG = "There is a problem processing this request due to technical difficulties.";

	public static final String ERROR_INTERNAL_SERVER_ERROR_MSG = "Something went wrong with Orbipay. The request could not be processed";
	public static final String ERROR_INTERNAL_SERVER_ERROR_CODE = "500";

	public static final String ERROR_NO_RESULTS_RETURNED_RESPONSE_CODE = "error_no_results_found";
	public static final String ERROR_NO_RESULTS_RETURNED_RESPONSE_MSG = "The search returned no results.Please refine your search and try again.";
	public static final String COMMAPPENDER = ",";
	public static final String PIPEAPPENDER = "|";
	public static final String QUERY_PARAM_LOGGER_LEVEL = "level";
	public static final String QUERY_PARAM_APPENDER_NAME = "name";

}
