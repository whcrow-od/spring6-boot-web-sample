package ua.od.whcrow.samples.spring6.boot_web._global.security;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.RequestMappingMethodAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.SecurityConstants;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
	
	@Value(SecurityConstants.AP_VP_SECURITY_ADMIN_AUTHORITY)
	private String adminAuthority;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(LocalIdentityService localIdentityService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(localIdentityService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	// TODO: organize ACL, https://www.baeldung.com/spring-security-acl
	//  https://www.baeldung.com/role-and-privilege-for-spring-security-registration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthSuccessHandler authSuccessHandler,
			RequestMappingMethodAccess requestMappingMethodAccess)
			throws Exception {
		httpSecurity.authorizeHttpRequests(registry -> {
			configureActuatorAccess(registry);
			requestMappingMethodAccess.configure(registry);
			registry.requestMatchers("/assets/**").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.anyRequest().authenticated();
		});
		// TODO: Support both auth methods - login page and HTTP basic auth
		// Use HTTP Basic authentication
		//httpSecurity.httpBasic(Customizer.withDefaults());
		httpSecurity.formLogin(configurer -> configurer
				.loginPage("/login")
				.loginProcessingUrl("/authenticate")
				.successHandler(authSuccessHandler)
				.permitAll()
		);
		httpSecurity.logout(configurer -> configurer
				.logoutUrl("/logout")
				.permitAll()
		);
		httpSecurity.exceptionHandling(configurer -> configurer
				.accessDeniedPage("/access-denied")
		);
		//httpSecurity.csrf(configurer -> configurer.disable());
		httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
		return httpSecurity.build();
	}
	
	private void configureActuatorAccess(
			@Nonnull AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
		if (adminAuthority == null) {
			registry.requestMatchers(EndpointRequest.toAnyEndpoint()).denyAll();
		} else {
			registry.requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(adminAuthority);
		}
	}
	
}
