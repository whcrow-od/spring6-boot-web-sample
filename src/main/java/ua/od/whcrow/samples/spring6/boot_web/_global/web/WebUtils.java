package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

public final class WebUtils {
	
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
		Assert.notNull(request, "request");
		return getServletRequestAttributes(request).getRequest();
	}
	
	public static boolean isApiRequest(@Nonnull HttpServletRequest request, @Nonnull String apiPath) {
		Assert.notNull(request, "request");
		Assert.notNull(apiPath, "apiPath");
		return apiPath.endsWith("/") ? request.getRequestURI().startsWith(apiPath)
				: request.getRequestURI().equals(apiPath);
	}
	
	public static boolean isApiRequest(@Nonnull HttpServletRequest request) {
		return isApiRequest(request, WebConstants.REQ_P_API_ROOT);
	}
	
	public static boolean isApiRequest(@Nonnull WebRequest request, @Nonnull String apiPath) {
		return isApiRequest(getHttpServletRequest(request), apiPath);
	}
	
	public static boolean isApiRequest(@Nonnull WebRequest request) {
		return isApiRequest(request, WebConstants.REQ_P_API_ROOT);
	}
	
}
