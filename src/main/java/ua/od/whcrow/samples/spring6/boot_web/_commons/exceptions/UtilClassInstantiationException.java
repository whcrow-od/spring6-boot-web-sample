package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;

public class UtilClassInstantiationException extends AbstractFormattedException {
	
	private static final String MSG = "The following class is considered to be an utility class and must not be instantiated: {}";
	
	public UtilClassInstantiationException(@Nonnull Class<?> utilClass) {
		super(MSG, utilClass.getName());
	}
	
}
