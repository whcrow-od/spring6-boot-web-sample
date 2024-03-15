package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;

public interface PasswordProvider {
	
	@Nonnull
	String getHint();
	
	@Nonnull
	String random();
	
}
