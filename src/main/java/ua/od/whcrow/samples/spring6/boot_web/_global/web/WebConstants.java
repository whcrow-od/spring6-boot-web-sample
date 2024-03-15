package ua.od.whcrow.samples.spring6.boot_web._global.web;

import ua.od.whcrow.samples.spring6.boot_web._commons.Constants;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;

public final class WebConstants {
	
	// AP_N - application property name
	public static final String AP_N_DEFAULT_LOCALE = "app.web.defaultLocale";
	public static final String AP_N_ERROR_VIEW_NAME_PREFIX = "app.web.error.view.name.prefix";
	public static final String AP_N_ERROR_DEFAULT_VIEW_NAME_SUFFIX = "app.web.error.defaultView.name.suffix";
	
	// AP_DV - application property default value
	public static final String AP_DV_DEFAULT_LOCALE = "#{null}";
	public static final String AP_DV_ERROR_VIEW_NAME_PREFIX = "errors/";
	public static final String AP_DV_ERROR_DEFAULT_VIEW_NAME_SUFFIX = "default";
	
	// AP_VP - application property value placeholder
	public static final String AP_VP_DEFAULT_LOCALE = Constants.AP_VP_PREFIX + AP_N_DEFAULT_LOCALE
			+ Constants.AP_VP_DELIMITER + AP_DV_DEFAULT_LOCALE + Constants.AP_VP_SUFFIX;
	public static final String AP_VP_ERROR_VIEW_NAME_PREFIX = Constants.AP_VP_PREFIX + AP_N_ERROR_VIEW_NAME_PREFIX
			+ Constants.AP_VP_DELIMITER + AP_DV_ERROR_VIEW_NAME_PREFIX + Constants.AP_VP_SUFFIX;
	public static final String AP_VP_ERROR_DEFAULT_VIEW_NAME_SUFFIX = Constants.AP_VP_PREFIX
			+ AP_N_ERROR_DEFAULT_VIEW_NAME_SUFFIX + Constants.AP_VP_DELIMITER + AP_DV_ERROR_DEFAULT_VIEW_NAME_SUFFIX
			+ Constants.AP_VP_SUFFIX;
	
	// REQ_P - request path
	public static final String REQ_P_ROOT = "/";
	public static final String REQ_P_API = "/api";
	public static final String REQ_P_API_ROOT = REQ_P_API + REQ_P_ROOT;
	
	// SA_N - session attribute name
	public static final String SA_N_USER = "user";
	
	// MA_N - model attribute name
	public static final String MA_N_ERROR = "error";
	public static final String MA_N_APP_NAME = "appName";
	public static final String MA_N_SERVER_DATE_TIME = "serverDateTime";
	public static final String MA_N_REQUEST_URL_PATH = "requestUrlPath";
	public static final String MA_N_USER = "currentUser";
	
	private WebConstants()
			throws ConstantsClassInstantiationException {
		throw new ConstantsClassInstantiationException(WebConstants.class);
	}
	
}
