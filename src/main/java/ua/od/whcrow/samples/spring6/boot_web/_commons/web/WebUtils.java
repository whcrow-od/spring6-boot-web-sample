package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.Enumeration;
import java.util.Set;

public final class WebUtils {
	
	private static final Set<String> API_MEDIA_TYPES = Set.of(
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
	);
	
	private WebUtils()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(WebUtils.class);
	}
	
	private static class HttpServletRequestProvider extends ServletUriComponentsBuilder {
		
		protected static HttpServletRequest get() {
			return getCurrentRequest();
		}
		
	}
	
	@Nonnull
	public static HttpServletRequest getCurrentHttpServletRequest() {
		return HttpServletRequestProvider.get();
	}
	
	@Nonnull
	public static ServletRequestAttributes getServletRequestAttributes(@Nonnull WebRequest request) {
		Assert.notNull(request, "request");
		Assert.state(request instanceof ServletRequestAttributes, ServletRequestAttributes.class.getSimpleName());
		return (ServletRequestAttributes) request;
	}
	
	@Nonnull
	public static HttpServletRequest getHttpServletRequest(@Nonnull WebRequest request) {
		return getServletRequestAttributes(request).getRequest();
	}
	
	private static boolean accepts(@Nonnull Enumeration<String> acceptTypes, @Nonnull String mediaType) {
		while (acceptTypes.hasMoreElements()) {
			String acceptType = acceptTypes.nextElement();
			if (acceptType.equals(mediaType)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isHtmlRequested(@Nonnull Enumeration<String> acceptTypes) {
		return accepts(acceptTypes, MediaType.TEXT_HTML_VALUE);
	}
	
	public static boolean isResponseEntityRequested(@Nonnull HttpServletRequest request) {
		Assert.notNull(request, "request");
		Enumeration<String> acceptTypes = request.getHeaders(HttpHeaders.ACCEPT);
		return !isHtmlRequested(acceptTypes)
				&& API_MEDIA_TYPES.stream().anyMatch(acceptType -> accepts(acceptTypes, acceptType));
	}
	
	public static boolean isApiPathRequested(@Nonnull HttpServletRequest request, @Nonnull String apiPath) {
		Assert.notNull(request, "request");
		Assert.notNull(apiPath, "apiPath");
		return apiPath.endsWith("/") ? request.getRequestURI().startsWith(apiPath)
				: request.getRequestURI().equals(apiPath);
	}
	
	public static boolean isApiRequest(@Nonnull HttpServletRequest request, @Nonnull String apiPath) {
		return isApiPathRequested(request, apiPath) || isResponseEntityRequested(request);
	}
	
}
