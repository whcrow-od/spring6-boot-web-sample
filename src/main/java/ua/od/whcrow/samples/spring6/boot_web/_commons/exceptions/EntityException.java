package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.AbstractFormattedException;

public class EntityException extends AbstractFormattedException {
	
	public EntityException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public EntityException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
}
