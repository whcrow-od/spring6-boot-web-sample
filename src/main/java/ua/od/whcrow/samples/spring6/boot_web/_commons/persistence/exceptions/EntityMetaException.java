package ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FormattedRuntimeException;

public class EntityMetaException extends FormattedRuntimeException {
	
	private final Class<?> entityClass;
	
	public EntityMetaException(@Nonnull Class<?> entityClass, @Nonnull String message, Object... args) {
		super(message, args);
		this.entityClass = entityClass;
	}
	
	public EntityMetaException(@Nonnull Class<?> entityClass, @Nonnull String message, @Nonnull Throwable cause,
			Object... args) {
		super(message, cause, args);
		this.entityClass = entityClass;
	}
	
	@Nonnull
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
}
