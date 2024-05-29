package ua.od.whcrow.samples.spring6.boot_web.samples;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._global.web.WebConstants;

public final class SamplesConstants {
	
	// BN - bean name
	public static final String BN_SAMPLES_JACKSON_FILTER_PROVIDER = "samplesJacksonFilterProvider";
	
	// REQ_P - request path
	public static final String REQ_P_SAMPLES = "/samples";
	public static final String REQ_P_API_SAMPLES = WebConstants.REQ_P_API + REQ_P_SAMPLES;
	
	private SamplesConstants()
			throws ConstantsClassInstantiationException {
		throw new ConstantsClassInstantiationException(SamplesConstants.class);
	}
	
}
