package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import jakarta.annotation.Nonnull;

import java.util.Map;

public class ParameterException extends BadRequestException {
	
	private static final String MSG = "Found {} invalid parameter/s";
	
	private final Map<String,Object> parameters;
	
	public ParameterException(@Nonnull String message, @Nonnull Map<String,Object> parameters, Object... args) {
		super(message, args);
		this.parameters = parameters;
	}
	
	public ParameterException(@Nonnull String message, @Nonnull Throwable cause, @Nonnull Map<String,Object> parameters,
			Object... args) {
		super(message, cause, args);
		this.parameters = parameters;
	}
	
	public ParameterException(@Nonnull Map<String,Object> parameters) {
		this(MSG, parameters, parameters.size());
	}
	
	public ParameterException(@Nonnull Throwable cause, @Nonnull Map<String,Object> parameters) {
		this(MSG, cause, parameters, parameters.size());
	}
	
	@Nonnull
	public Map<String,Object> getParameters() {
		return parameters;
	}
	
}
