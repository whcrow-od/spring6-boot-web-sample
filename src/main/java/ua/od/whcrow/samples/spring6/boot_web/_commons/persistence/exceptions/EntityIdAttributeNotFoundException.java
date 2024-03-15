package ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions;

import jakarta.annotation.Nonnull;

public class EntityIdAttributeNotFoundException extends EntityAttributeNotFoundException {
	
	private static final String ID = "<ID>";
	
	public EntityIdAttributeNotFoundException(@Nonnull Class<?> entityClass) {
		super(entityClass, ID);
	}
	
	public EntityIdAttributeNotFoundException(@Nonnull Class<?> entityClass, @Nonnull Throwable cause) {
		super(entityClass, ID, cause);
	}
	
}
