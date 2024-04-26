package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.PublicMessageException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;

public class StatusException extends ResponseStatusException implements PublicMessageException {
	
	public StatusException(@Nonnull HttpStatus status, @Nonnull String message, Object... args) {
		super(Assert.notNull(status, "status"), Msg.format(Assert.notNull(message, "message"), args));
	}
	
	public StatusException(@Nonnull HttpStatus status, @Nonnull String message, @Nonnull Throwable cause,
			Object... args) {
		super(Assert.notNull(status, "status"), Msg.format(Assert.notNull(message, "message"), args),
				Assert.notNull(cause, "cause"));
	}
	
	@Nonnull
	public HttpStatus getStatus() {
		return (HttpStatus) getStatusCode();
	}
	
	@Nonnull
	@Override
	public String getMessage() {
		return getReason();
	}
	
}
