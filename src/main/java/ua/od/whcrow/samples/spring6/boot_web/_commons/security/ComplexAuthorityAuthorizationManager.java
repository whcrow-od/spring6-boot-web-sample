package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.AssertChain;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ComplexAuthorityAuthorizationManager extends AdminAuthorizationManager {
	
	private final List<Set<String>> complexAuthorities;
	
	public ComplexAuthorityAuthorizationManager(@Nullable String adminAuthority,
			@Nonnull List<Set<String>> complexAuthorities) {
		super(adminAuthority);
		AssertChain.of(complexAuthorities, "complexAuthorities")
				.chain(Assert::notEmpty)
				.chain(Assert::noNullElements);
		for (Set<String> authorities : complexAuthorities) {
			AssertChain.of(authorities, "authorities")
					.chain(Assert::notEmpty)
					.chain(Assert::noNullElements);
		}
		this.complexAuthorities = complexAuthorities;
	}
	
	@Override
	public AuthorizationDecision check(@Nonnull Supplier<Authentication> authenticationSupplier,
			@Nonnull RequestAuthorizationContext authorizationContext) {
		Authentication authentication = authenticationSupplier.get();
		if (isAdmin(authentication)) {
			return GRANTED;
		}
		for (Set<String> authorities : complexAuthorities) {
			boolean noMatch = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.noneMatch(authorities::contains);
			if (noMatch) {
				return NOT_GRANTED;
			}
		}
		return GRANTED;
	}
	
}
