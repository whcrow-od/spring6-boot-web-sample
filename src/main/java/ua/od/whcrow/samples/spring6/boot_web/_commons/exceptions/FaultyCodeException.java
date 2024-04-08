package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;

public class FaultyCodeException extends FormattedRuntimeException{
	
	public FaultyCodeException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public FaultyCodeException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
}
