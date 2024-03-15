package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import jakarta.annotation.Nonnull;

public class FormattedRuntimeException extends RuntimeException {
	
	public FormattedRuntimeException(@Nonnull String message, Object... args) {
		super(Msg.format(message, args));
	}
	
	public FormattedRuntimeException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(Msg.format(message, args), cause);
	}
	
}
