package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;

public class ConstantsClassInstantiationException extends AbstractFormattedException {
	
	private static final String MSG = "The following class is considered to be a constants class and must not be instantiated: {}";
	
	public ConstantsClassInstantiationException(@Nonnull Class<?> constantsClass) {
		super(MSG, constantsClass.getName());
	}
	
}
