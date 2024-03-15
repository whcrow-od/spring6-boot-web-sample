package ua.od.whcrow.samples.spring6.boot_web._global.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface IdentityInfo extends UserDetails {
	
	String getId();
	
	Collection<String> getRoles();
	
	String getEmail();
	
	String getFirstName();
	
	String getLastName();
	
	String getNickname();
	
}
