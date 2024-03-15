package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.context.request.WebRequest;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.time.ZonedDateTime;

@Getter
@ToString
public class BodyModel<T> {
	
	@ToString.Exclude
	private final HttpStatusCode statusCode;
	private final int status;
	private final T payload;
	private final String details;
	
	private final String method;
	private final String endpoint;
	private final ZonedDateTime datetime = ZonedDateTime.now();
	
	public BodyModel(@Nonnull HttpServletRequest request, @Nonnull HttpStatusCode status, @Nullable T payload,
			@Nullable String details) {
		Assert.notNull(request, "request");
		Assert.notNull(status, "status");
		this.statusCode = status;
		this.status = status.value();
		this.payload = payload;
		this.details = details;
		this.method = request.getMethod();
		this.endpoint = request.getRequestURI();
	}
	
	public BodyModel(@Nonnull HttpStatusCode status, @Nullable T payload, @Nullable String details) {
		this(WebUtils.getCurrentHttpServletRequest(), status, payload, details);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatusCode status, @Nullable T payload,
			@Nullable String details) {
		this(WebUtils.getHttpServletRequest(request), status, payload, details);
	}
	
	public BodyModel(@Nonnull WebRequest request, @Nonnull HttpStatusCode status, @Nullable T payload) {
		this(request, status, payload, null);
	}
	
	public BodyModel(@Nonnull HttpStatusCode status, @Nullable T payload) {
		this(status, payload, null);
	}
	
	public BodyModel(@Nonnull HttpStatusCode status) {
		this(status, null);
	}
	
	@JsonIgnore
	@Nonnull
	public HttpStatusCode getStatusCode() {
		return statusCode;
	}
	
}
