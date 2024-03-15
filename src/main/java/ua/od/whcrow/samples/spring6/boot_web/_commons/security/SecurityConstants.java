package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import ua.od.whcrow.samples.spring6.boot_web._commons.Constants;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;

public final class SecurityConstants {
	
	// AP_N - application property name
	public static final String AP_N_SECURITY_ADMIN_AUTHORITY = "app.security.admin";
	public static final String AP_N_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS = "app.security.mappedRequest.adminAccess";
	
	// AP_DV - application property default value
	public static final String AP_DV_SECURITY_ADMIN_AUTHORITY = "ROLE_ADMIN";
	public static final String AP_DV_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS ="true";
	
	// AP_VP - application property value placeholder
	public static final String AP_VP_SECURITY_ADMIN_AUTHORITY = Constants.AP_VP_PREFIX + AP_N_SECURITY_ADMIN_AUTHORITY
			+ Constants.AP_VP_DELIMITER + AP_DV_SECURITY_ADMIN_AUTHORITY + Constants.AP_VP_SUFFIX;
	public static final String AP_VP_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS = Constants.AP_VP_PREFIX
			+ AP_N_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS + Constants.AP_VP_DELIMITER
			+ AP_DV_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS + Constants.AP_VP_SUFFIX;
	
	private SecurityConstants()
			throws ConstantsClassInstantiationException {
		throw new ConstantsClassInstantiationException(SecurityConstants.class);
	}
	
}
