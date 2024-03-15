package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class GeneralAccessAuthorizationManager extends AdminAuthorizationManager {
	
	private static final AuthenticationTrustResolver TRUST_RESOLVER = new AuthenticationTrustResolverImpl();
	
	private final GeneralAccessType accessType;
	
	public GeneralAccessAuthorizationManager(@Nullable String adminAuthority, @Nonnull GeneralAccessType accessType) {
		super(adminAuthority);
		this.accessType = accessType;
	}
	
	@Override
	public AuthorizationDecision check(@Nonnull Supplier<Authentication> authenticationSupplier,
			@Nonnull RequestAuthorizationContext authorizationContext) {
		Authentication authentication = authenticationSupplier.get();
		if (isAdmin(authentication)) {
			return GRANTED;
		}
		return switch (accessType) {
			case ANYONE -> GRANTED;
			case ANONYMOUS_ONLY -> TRUST_RESOLVER.isAnonymous(authentication) ? GRANTED : NOT_GRANTED;
			case AUTHENTICATED -> authentication.isAuthenticated() ? GRANTED : NOT_GRANTED;
			case AUTHENTICATED_FULLY -> TRUST_RESOLVER.isFullyAuthenticated(authentication) ? GRANTED : NOT_GRANTED;
			case NONE -> NOT_GRANTED;
		};
	}
	
}
