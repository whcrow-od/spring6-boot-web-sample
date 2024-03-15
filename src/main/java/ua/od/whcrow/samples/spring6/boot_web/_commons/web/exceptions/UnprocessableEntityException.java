package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnprocessableEntityException extends StatusException {
	
	private static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;
	private static final String MSG = "Found {} invalid property/ies";
	
	private final Map<String,String> parameters;
	
	public UnprocessableEntityException(@Nonnull String message, @Nonnull Map<String,String> parameters,
			Object... args) {
		super(STATUS, message, args);
		this.parameters = parameters;
	}
	
	public UnprocessableEntityException(@Nonnull String message, @Nonnull Throwable cause,
			@Nonnull Map<String,String> parameters, Object... args) {
		super(STATUS, message, cause, args);
		this.parameters = parameters;
	}
	
	public UnprocessableEntityException(@Nonnull Map<String,String> parameters) {
		this(MSG, parameters, parameters.size());
	}
	
	public UnprocessableEntityException(@Nonnull Throwable cause, @Nonnull Map<String,String> parameters) {
		this(MSG, cause, parameters, parameters.size());
	}
	
	@Nonnull
	public Map<String,String> getParameters() {
		return parameters;
	}
	
}
