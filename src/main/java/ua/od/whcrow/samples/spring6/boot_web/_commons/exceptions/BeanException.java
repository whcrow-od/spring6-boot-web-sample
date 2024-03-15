package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;

public class BeanException extends FormattedRuntimeException {
	
	public BeanException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public BeanException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
}
