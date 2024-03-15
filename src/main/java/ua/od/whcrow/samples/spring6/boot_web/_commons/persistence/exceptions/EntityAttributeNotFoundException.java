package ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions;

import jakarta.annotation.Nonnull;

public class EntityAttributeNotFoundException extends EntityAttributeException {
	
	private static final String MSG = "Entity '{}' attribute '{}' not found";
	
	public EntityAttributeNotFoundException(@Nonnull Class<?> entityClass, @Nonnull String attribute) {
		super(entityClass, attribute, MSG, entityClass.getName(), attribute);
	}
	
	public EntityAttributeNotFoundException(@Nonnull Class<?> entityClass, @Nonnull String attribute,
			@Nonnull Throwable cause) {
		super(entityClass, attribute, MSG, cause, entityClass.getName(), attribute);
	}
	
}
