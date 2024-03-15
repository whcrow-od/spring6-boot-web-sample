package ua.od.whcrow.samples.spring6.boot_web._global.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LocalIdentityService extends UserDetailsService {
	
	@Nonnull
	@Override
	IdentityInfo loadUserByUsername(@Nonnull String username)
			throws UsernameNotFoundException;
	
}
