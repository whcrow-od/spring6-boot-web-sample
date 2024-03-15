package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.AbstractFormattedException;
import jakarta.annotation.Nonnull;

public class DetailedException extends AbstractFormattedException {
	
	public DetailedException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public DetailedException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
}
