package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import jakarta.annotation.Nonnull;

public abstract class AbstractFormattedException extends Exception {
	
	public AbstractFormattedException(@Nonnull String message, Object... args) {
		super(Msg.format(message, args));
	}
	
	public AbstractFormattedException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(Msg.format(message, args), cause);
	}
	
}
