package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

@Lazy
@Component
public class RequestMappingMethodCsrfConfigurer {
	
	private final RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	// There can be multiple RequestMappingHandlerMapping registered, so @Qualifier is necessary for injecting a default one.
	// For example, Spring Actuator also provides its own RequestMappingHandlerMapping implementation.
	public RequestMappingMethodCsrfConfigurer(
			@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}
	
	@Nonnull
	public CsrfConfigurer<HttpSecurity> configure(@Nonnull CsrfConfigurer<HttpSecurity> csrfConfigurer) {
		Assert.notNull(csrfConfigurer, "csrfConfigurer");
		requestMappingHandlerMapping.getHandlerMethods().forEach((mapping, method) -> {
			CsrfIgnore csrfIgnore = AnnotatedElementUtils.getMergedAnnotation(
					method.getMethod(), CsrfIgnore.class);
			if (csrfIgnore != null) {
				handle(mapping, csrfIgnore, csrfConfigurer);
			}
			csrfIgnore = AnnotatedElementUtils.getMergedAnnotation(method.getBeanType(), CsrfIgnore.class);
			if (csrfIgnore != null) {
				handle(mapping, csrfIgnore, csrfConfigurer);
			}
		});
		return csrfConfigurer;
	}
	
	private void handle(@Nonnull RequestMappingInfo mapping, @Nonnull CsrfIgnore csrfIgnore,
			@Nonnull CsrfConfigurer<HttpSecurity> csrfConfigurer) {
		RequestMatcher requestMatcher = new RequestMappingRequestMatcher(mapping);
		if (csrfIgnore.value()) {
			csrfConfigurer.ignoringRequestMatchers(requestMatcher);
			return;
		}
		csrfConfigurer.requireCsrfProtectionMatcher(requestMatcher);
	}
	
}
