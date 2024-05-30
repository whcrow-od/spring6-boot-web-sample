package ua.od.whcrow.samples.spring6.boot_web._global.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.RequestMappingMethodAccessConfigurer;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.RequestMappingMethodCsrfConfigurer;
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
			RequestMappingMethodAccessConfigurer requestMappingMethodAccessConfigurer,
			RequestMappingMethodCsrfConfigurer requestMappingMethodCsrfConfigurer)
			throws Exception {
		httpSecurity.authorizeHttpRequests(registry -> requestMappingMethodAccessConfigurer.configure(registry)
				.requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(adminAuthority)
				// TODO: No hardcode for assets path
				.requestMatchers("/assets/**").permitAll()
				// TODO: No hardcode for H2 console path
				.requestMatchers("/h2-console/**").hasAuthority(adminAuthority)
				.anyRequest().authenticated());
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
		httpSecurity.csrf(csrf -> requestMappingMethodCsrfConfigurer.configure(csrf)
				.ignoringRequestMatchers("/h2-console/**"));
		return httpSecurity.build();
	}
	
}
