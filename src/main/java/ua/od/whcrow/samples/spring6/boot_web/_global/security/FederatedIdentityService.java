package ua.od.whcrow.samples.spring6.boot_web._global.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.Authentication;

public interface FederatedIdentityService {
	
	@Nonnull
	IdentityInfo save(@Nonnull Authentication authentication);
	
}
