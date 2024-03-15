package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.function.Supplier;

public class AdminAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	
	protected static final AuthorizationDecision GRANTED = new AuthorizationDecision(true);
	protected static final AuthorizationDecision NOT_GRANTED = new AuthorizationDecision(false);
	
	protected final String adminAuthority;
	
	public AdminAuthorizationManager(@Nullable String adminAuthority) {
		Assert.isNullOrNotBlank(adminAuthority, "adminAuthority");
		this.adminAuthority = adminAuthority;
	}
	
	protected boolean isAdmin(@Nonnull Authentication authentication) {
		Assert.notNull(authentication, "authentication");
		return adminAuthority != null && authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> adminAuthority.equals(grantedAuthority.getAuthority()));
	}
	
	@Override
	public AuthorizationDecision check(@Nonnull Supplier<Authentication> authenticationSupplier,
			@Nonnull RequestAuthorizationContext authorizationContext) {
		return isAdmin(authenticationSupplier.get()) ? GRANTED : NOT_GRANTED;
	}
	
}
