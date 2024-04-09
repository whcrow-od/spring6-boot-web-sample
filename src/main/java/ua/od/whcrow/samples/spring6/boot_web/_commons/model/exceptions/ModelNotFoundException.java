package ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class ModelNotFoundException extends ModelException {
	
	private static final String MSG_OF_ID = "Model {} with the following ID not found: {}";
	
	public ModelNotFoundException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public ModelNotFoundException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
	@Nonnull
	public static ModelNotFoundException ofId(@Nonnull Class<?> modelType, @Nullable Object id) {
		return new ModelNotFoundException(MSG_OF_ID, modelType.getName(), id);
	}
	
	@Nonnull
	public static ModelNotFoundException ofId(@Nonnull Class<?> modelType, @Nullable Object id,
			@Nonnull Throwable cause) {
		return new ModelNotFoundException(MSG_OF_ID, cause, modelType.getName(), id);
	}
	
}
