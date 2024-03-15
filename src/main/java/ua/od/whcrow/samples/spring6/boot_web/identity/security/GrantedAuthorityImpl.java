package ua.od.whcrow.samples.spring6.boot_web.identity.security;

import org.springframework.security.core.GrantedAuthority;

record GrantedAuthorityImpl(
		String authority
) implements GrantedAuthority {
	
	@Override
	public String getAuthority() {
		return authority();
	}
	
}
