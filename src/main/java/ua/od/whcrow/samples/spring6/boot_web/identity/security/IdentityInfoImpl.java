package ua.od.whcrow.samples.spring6.boot_web.identity.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import ua.od.whcrow.samples.spring6.boot_web._global.security.IdentityInfo;

import java.util.Collection;

record IdentityInfoImpl(
		@Getter
		Collection<? extends GrantedAuthority> authorities,
		
		@Getter
		String password,
		
		@Getter
		String username,
		
		@Getter
		boolean accountNonExpired,
		
		@Getter
		boolean accountNonLocked,
		
		@Getter
		boolean credentialsNonExpired,
		
		@Getter
		boolean enabled,
		
		@Getter
		String id,
		
		@Getter
		Collection<String> roles,
		
		@Getter
		String email,
		
		@Getter
		String firstName,
		
		@Getter
		String lastName,
		
		@Getter
		String nickname
) implements IdentityInfo {}
