package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.context.request.WebRequest;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

@Getter
@ToString
public class BodyModel<T> {
	
	private final HttpStatus status;
	private final T payload;
	private final String details;
	
	private final String method;
	private final String endpoint;
	
	public BodyModel(@Nonnull HttpServletRequest request, @Nonnull HttpStatus status, @Nullable T payload,
			@Nullable String details) {
		Assert.notNull(request, "request");
		Assert.notNull(status, "status");
		this.status = status;
		this.payload = payload;
		this.details = details;
		this.method = request.getMethod();
		this.endpoint = request.getRequestURI();
	}
	
	public BodyModel(@Nonnull HttpServletRequest request, @Nonnull HttpStatusCode statusCode, @Nullable T payload,
			@Nullable String details) {
		this(request, HttpStatus.valueOf(statusCode.value()), payload, details);
	}
	
	public BodyModel(@Nonnull HttpStatus status, @Nullable T payload, @Nullable String details) {
		this(WebUtils.getCurrentHttpServletRequest(), status, payload, details);
	}
	
	public BodyModel(@Nonnull HttpStatusCode statusCode, @Nullable T payload, @Nullable String details) {
		this(HttpStatus.valueOf(statusCode.value()), payload, details);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatus status, @Nullable T payload,
			@Nullable String details) {
		this(WebUtils.getHttpServletRequest(request), status, payload, details);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatusCode statusCode, @Nullable T payload,
			@Nullable String details) {
		this(request, HttpStatus.valueOf(statusCode.value()), payload, details);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatus status, @Nullable T payload) {
		this(request, status, payload, null);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatusCode statusCode, @Nullable T payload) {
		this(request, HttpStatus.valueOf(statusCode.value()), payload);
	}
	
	public BodyModel(@Nonnull HttpStatus status, @Nullable T payload) {
		this(status, payload, null);
	}
	
	public BodyModel(@Nonnull HttpStatusCode statusCode, @Nullable T payload) {
		this(HttpStatus.valueOf(statusCode.value()), payload);
	}
	
	public BodyModel(@Nonnull HttpStatus status) {
		this(status, null);
	}
	
	public BodyModel(@Nonnull HttpStatusCode statusCode) {
		this(HttpStatus.valueOf(statusCode.value()));
	}
	
}
