package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Lazy
@Component
public class RequestMappingMethodAccessConfigurer {
	
	@Value(SecurityConstants.AP_VP_SECURITY_ADMIN_AUTHORITY)
	private String adminAuthority;
	
	@Value(SecurityConstants.AP_VP_SECURITY_MAPPED_REQUEST_ADMIN_ACCESS)
	private boolean adminAccess;
	
	private final RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	// There can be multiple RequestMappingHandlerMapping registered, so @Qualifier is necessary for injecting a default one.
	// For example, Spring Actuator also provides its own RequestMappingHandlerMapping implementation.
	public RequestMappingMethodAccessConfigurer(
			@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}
	
	@Nonnull
	public AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configure(
			@Nonnull AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
		Assert.notNull(registry, "registry");
		requestMappingHandlerMapping.getHandlerMethods().forEach((mapping, method) -> {
			Supplier<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl> matchRequestsSupplier =
					() -> registry.requestMatchers(new RequestMappingRequestMatcher(mapping));
			Set<AuthorityAccess> authorityAccessSet = AnnotatedElementUtils.getMergedRepeatableAnnotations(
					method.getMethod(), AuthorityAccess.class);
			if (!authorityAccessSet.isEmpty()) {
				handle(authorityAccessSet, matchRequestsSupplier);
				return;
			}
			GeneralAccess generalAccess = AnnotatedElementUtils.getMergedAnnotation(method.getMethod(),
					GeneralAccess.class);
			if (generalAccess != null) {
				handle(generalAccess, matchRequestsSupplier);
				return;
			}
			authorityAccessSet = AnnotatedElementUtils.getMergedRepeatableAnnotations(method.getBeanType(),
					AuthorityAccess.class);
			if (!authorityAccessSet.isEmpty()) {
				handle(authorityAccessSet, matchRequestsSupplier);
				return;
			}
			generalAccess = AnnotatedElementUtils.getMergedAnnotation(method.getBeanType(), GeneralAccess.class);
			if (generalAccess != null) {
				handle(generalAccess, matchRequestsSupplier);
			}
		});
		return registry;
	}
	
	private void handle(@Nonnull Set<AuthorityAccess> authorityAccessSet,
			@Nonnull Supplier<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl> requestMatchingSupplier) {
		List<Set<String>> complexAuthorities = authorityAccessSet.stream()
				.map(a -> Set.of(a.value()))
				.collect(Collectors.toList());
		var manager = new ComplexAuthorityAuthorizationManager(adminAccess ? adminAuthority : null, complexAuthorities);
		requestMatchingSupplier.get().access(manager);
	}
	
	private void handle(@Nonnull GeneralAccess generalAccess,
			@Nonnull Supplier<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl> requestMatchingSupplier) {
		var manager = new GeneralAccessAuthorizationManager(adminAccess ? adminAuthority : null, generalAccess.value());
		requestMatchingSupplier.get().access(manager);
	}
	
}
