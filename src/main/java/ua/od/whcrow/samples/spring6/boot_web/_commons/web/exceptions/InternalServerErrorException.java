package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends StatusException {
	
	private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public InternalServerErrorException(@Nonnull String message, Object... args) {
		super(STATUS, message, args);
	}
	
	public InternalServerErrorException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(STATUS, message, cause, args);
	}
	
}
