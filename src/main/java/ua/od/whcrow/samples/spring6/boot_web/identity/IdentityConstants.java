package ua.od.whcrow.samples.spring6.boot_web.identity;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._global.web.WebConstants;

public final class IdentityConstants {
	
	// REQ_P - request path
	public static final String REQ_P_IDENTITIES = "/identities";
	public static final String REQ_P_API_IDENTITIES = WebConstants.REQ_P_API + REQ_P_IDENTITIES;
	
	private IdentityConstants()
			throws ConstantsClassInstantiationException {
		throw new ConstantsClassInstantiationException(IdentityConstants.class);
	}
	
}
