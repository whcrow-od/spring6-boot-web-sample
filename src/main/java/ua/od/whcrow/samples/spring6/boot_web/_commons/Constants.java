package ua.od.whcrow.samples.spring6.boot_web._commons;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;

public final class Constants {
	
	// AP_N - application property name
	public static final String AP_N_COMMONS_BEAN_FINDER_EAGER = "app.commons.beanfinder.eager";
	
	// AP_DV - application property default value
	public static final String AP_DV_COMMONS_BEAN_FINDER_EAGER = "true";
	
	// AP_VP - application property value placeholder
	public static final String AP_VP_PREFIX = "${";
	public static final String AP_VP_DELIMITER = ":";
	public static final String AP_VP_SUFFIX = "}";
	public static final String AP_VP_COMMONS_BEAN_FINDER_EAGER = AP_VP_PREFIX + AP_N_COMMONS_BEAN_FINDER_EAGER
			+ AP_VP_DELIMITER + AP_DV_COMMONS_BEAN_FINDER_EAGER + AP_VP_SUFFIX;
	
	private Constants()
			throws ConstantsClassInstantiationException {
		throw new ConstantsClassInstantiationException(Constants.class);
	}
	
}
