package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import org.springframework.context.annotation.Lazy;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Lazy
@Service
class PasswordBuilderProviderImpl implements PasswordBuilderProvider {
	
	@Nonnull
	@Override
	public RandomPasswordBuilder random() {
		return new BasicRandomPasswordBuilder();
	}
	
}
