package ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.AbstractFormattedException;

public class ModelException extends AbstractFormattedException {
	
	public ModelException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public ModelException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
}
