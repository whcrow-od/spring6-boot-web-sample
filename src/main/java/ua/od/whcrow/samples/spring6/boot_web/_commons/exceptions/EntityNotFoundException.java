package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class EntityNotFoundException extends EntityException {
	
	private static final String MSG_OF_ID = "Entity of type '{}' with the following ID not found: {}";
	
	public EntityNotFoundException(@Nonnull String message, Object... args) {
		super(message, args);
	}
	
	public EntityNotFoundException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(message, cause, args);
	}
	
	@Nonnull
	public static EntityNotFoundException ofId(@Nonnull Class<?> entityType, @Nullable Object id) {
		return new EntityNotFoundException(MSG_OF_ID, entityType.getName(), id);
	}
	
	@Nonnull
	public static EntityNotFoundException ofId(@Nonnull Class<?> entityType, @Nullable Object id,
			@Nonnull Throwable cause) {
		return new EntityNotFoundException(MSG_OF_ID, cause, entityType.getName(), id);
	}
	
}
