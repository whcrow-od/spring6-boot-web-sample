package ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions;

import jakarta.annotation.Nonnull;

public class EntityAttributeException extends EntityMetaException {
	
	private final String attribute;
	
	public EntityAttributeException(@Nonnull Class<?> entityClass, @Nonnull String attribute, @Nonnull String message,
			Object... args) {
		super(entityClass, message, args);
		this.attribute = attribute;
	}
	
	public EntityAttributeException(@Nonnull Class<?> entityClass, @Nonnull String attribute, @Nonnull String message,
			@Nonnull Throwable cause, Object... args) {
		super(entityClass, message, cause, args);
		this.attribute = attribute;
	}
	
	@Nonnull
	public String getAttribute() {
		return attribute;
	}
	
}
