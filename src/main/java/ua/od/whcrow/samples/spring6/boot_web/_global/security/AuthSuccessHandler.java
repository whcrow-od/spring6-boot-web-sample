package ua.od.whcrow.samples.spring6.boot_web._global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.od.whcrow.samples.spring6.boot_web._global.web.WebConstants;

import java.io.IOException;

@Component
class AuthSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final AuthenticationSuccessHandler DEFAULT_HANDLER =
			new SavedRequestAwareAuthenticationSuccessHandler();
	
	private final FederatedIdentityService federatedIdentityService;
	
	public AuthSuccessHandler(FederatedIdentityService federatedIdentityService) {
		this.federatedIdentityService = federatedIdentityService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication)
			throws IOException, ServletException {
		IdentityInfo userDetails;
		if (authentication.getPrincipal() instanceof IdentityInfo identityInfo) {
			userDetails = identityInfo;
		} else {
			userDetails = federatedIdentityService.save(authentication);
		}
		request.getSession().setAttribute(WebConstants.SA_N_USER, userDetails);
		DEFAULT_HANDLER.onAuthenticationSuccess(request, response, authentication);
	}
	
}
