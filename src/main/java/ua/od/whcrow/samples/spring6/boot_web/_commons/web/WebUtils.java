package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	
	@Nullable
	public static HttpServletResponse getHttpServletResponse(@Nonnull WebRequest request) {
		return getServletRequestAttributes(request).getResponse();
	}
	
	public static boolean isResponseCommitted(@Nonnull WebRequest request) {
		HttpServletResponse response = getHttpServletResponse(request);
		return response != null && response.isCommitted();
	}
	
	public static boolean isApiRequest(@Nonnull HttpServletRequest request) {
		Enumeration<String> acceptTypes = request.getHeaders(HttpHeaders.ACCEPT);
		while (acceptTypes.hasMoreElements()) {
			if (API_MEDIA_TYPES.contains(acceptTypes.nextElement())) {
				return true;
			}
		}
		return false;
	}
	
}
