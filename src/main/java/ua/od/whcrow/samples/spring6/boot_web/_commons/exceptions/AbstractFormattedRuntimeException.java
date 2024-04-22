package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;

public abstract class AbstractFormattedRuntimeException extends RuntimeException {
	
	public AbstractFormattedRuntimeException(@Nonnull String message, Object... args) {
		super(Msg.format(message, args));
	}
	
	public AbstractFormattedRuntimeException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(Msg.format(message, args), cause);
	}
	
}
