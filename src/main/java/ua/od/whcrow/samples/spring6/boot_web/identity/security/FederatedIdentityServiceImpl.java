package ua.od.whcrow.samples.spring6.boot_web.identity.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FormattedRuntimeException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._global.security.FederatedIdentityService;
import ua.od.whcrow.samples.spring6.boot_web._global.security.IdentityInfo;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

@Component
class FederatedIdentityServiceImpl implements FederatedIdentityService {
	
	private final IdentityService identityService;
	private final IdentityInfoMapper identityInfoMapper;
	
	public FederatedIdentityServiceImpl(IdentityService identityService, IdentityInfoMapper identityInfoMapper) {
		this.identityService = identityService;
		this.identityInfoMapper = identityInfoMapper;
	}
	
	@Nonnull
	@Override
	public IdentityInfo save(@Nonnull Authentication authentication) {
		Assert.notNull(authentication, "authentication");
		Identity identity = identityService.findByUsername(authentication.getName()).orElse(new Identity());
		if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
			mapFederatedIdentity(oidcUser, identity);
		} else if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
			mapFederatedIdentity(oAuth2User, identity);
		} else {
			throw new FormattedRuntimeException("Can't save principal '{}', because its type is not supported: {}",
					authentication.getName(), authentication.getPrincipal().getClass().getName());
		}
		identity = identityService.save(identity);
		return identityInfoMapper.map(identity);
	}
	
	private void mapFederatedIdentity(@Nonnull OidcUser oidcUser, @Nonnull Identity identity) {
		// TODO: Proceed with OidcUser and Federated Identity
	}
	
	private void mapFederatedIdentity(@Nonnull OAuth2User oAuth2User, @Nonnull Identity identity) {
		// TODO: Proceed with OAuth2User and Federated Identity
	}
	
}
