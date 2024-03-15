package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FormattedRuntimeException;
import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.SingularAttribute;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

record AttributeMetaImpl(
		@Nonnull
		String name,
		
		@Nonnull
		Class<?> type,
		
		boolean isId,
		
		boolean isCollection,
		
		@Nonnull Function<Object,Object> valueGetter
) implements AttributeMeta {
	
	@Nonnull
	static AttributeMetaImpl of(@Nonnull Attribute<?,?> attribute) {
		boolean isId = attribute instanceof SingularAttribute<?,?> singularAttribute && singularAttribute.isId();
		return new AttributeMetaImpl(attribute.getName(), attribute.getJavaType(), isId, attribute.isCollection(),
				getValueGetter(attribute));
	}
	
	@Nonnull
	static AttributeMetaImpl of(@Nonnull SingularAttribute<?,?> attribute) {
		return new AttributeMetaImpl(attribute.getName(), attribute.getJavaType(), attribute.isId(), false,
				getValueGetter(attribute));
	}
	
	@Nonnull
	@Override
	public String getName() {
		return name();
	}
	
	@Nonnull
	@Override
	public Class<?> getType() {
		return type();
	}
	
	@Override
	public Object getValue(@Nonnull Object entityObj) {
		return valueGetter.apply(entityObj);
	}
	
	@Nonnull
	private static Function<Object,Object> getValueGetter(@Nonnull Attribute<?,?> attribute) {
		if (attribute.getJavaMember() instanceof Field field) {
			return object -> {
				try {
					field.setAccessible(true);
					return field.get(object);
				} catch (InaccessibleObjectException | SecurityException | IllegalAccessException e) {
					throw new FormattedRuntimeException(
							"Failed to get a value from field {} of the following entity: {}", e, field, object);
				}
			};
		}
		if (attribute.getJavaMember() instanceof Method method) {
			return object -> {
				try {
					return method.invoke(object);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new FormattedRuntimeException(
							"Failed to get a value from method {} of the following entity: {}", e, method, object);
				}
			};
		}
		throw new FormattedRuntimeException("Failed to get a value from member like {} because it's not supported",
				attribute.getJavaMember());
	}
	
}
