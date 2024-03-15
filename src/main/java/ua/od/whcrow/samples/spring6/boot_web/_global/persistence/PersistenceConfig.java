package ua.od.whcrow.samples.spring6.boot_web._global.persistence;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@EnableJpaAuditing(
		auditorAwareRef = "auditorProvider"
)
@Configuration
class PersistenceConfig {
	
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAware<>() {
			@Nonnull
			@Override
			public Optional<String> getCurrentAuditor() {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails userDetails) {
					return Optional.of(userDetails.getUsername());
				}
				return Optional.of(String.valueOf(principal));
			}
		};
	}
	
}
