package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import ua.od.whcrow.samples.spring6.boot_web._commons.security.RandomPasswordBuilder;
import jakarta.annotation.Nonnull;

public interface PasswordBuilderProvider {
	
	@Nonnull
	RandomPasswordBuilder random();
	
}
