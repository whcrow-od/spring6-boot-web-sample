package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.AnnotationUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public abstract class Stringable implements Serializable {
	
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Ignore {}
	
	private Class<?> effectiveType;
	
	@Nonnull
	protected Class<?> _getEffectiveType() {
		if (effectiveType == null) {
			effectiveType = EntityUtils.getEntityType(this);
		}
		return effectiveType;
	}
	
	protected boolean _isEntityClass(@Nonnull Class<?> clazz) {
		return AnnotationUtils.getAnnotation(clazz, Entity.class) != null;
	}
	
	protected boolean _isCollectableWithinToString(@Nonnull Method method) {
		if (AnnotationUtils.getAnnotation(method, Ignore.class) != null) {
			return false;
		}
		if (AnnotationUtils.getAnnotation(method.getReturnType(), Ignore.class) != null) {
			return false;
		}
		if (_isEntityClass(method.getReturnType())) {
			return false;
		}
		if (!(method.getGenericReturnType() instanceof ParameterizedType parameterizedReturnType)) {
			return true;
		}
		for (Type actualTypeArgument : parameterizedReturnType.getActualTypeArguments()) {
			if (!(actualTypeArgument instanceof Class<?> actualClassArgument)) {
				return false;
			}
			if (AnnotationUtils.getAnnotation(actualClassArgument, Entity.class) != null) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean _isCollectableWithinToString(@Nullable Object propertyValue) {
		// Just to be absolutely sure that nothing lazy is going to be loaded during toString() call
		return EntityUtils.isInitialized(propertyValue);
	}
	
	/**
	 * Collects info only from the public getter methods that match the following conditions:<ul><li>method is NOT
	 * annotated with {@link Ignore}</li><li>method's return type is NOT annotated with {@link Ignore}</li><li>method's
	 * return type is NOT representing an entity</li><li>method's return type is NOT a parameterized type that contains
	 * an argument of type representing an entity</li><li>method's return type is NOT a parameterized type that contains
	 * an argument of non-resolved type</ul>This prevents initializing of lazy-loaded properties and crawling across the
	 * circular references (bidirectional relations etc.)
	 */
	@SneakyThrows
	@Nonnull
	@Override
	public final String toString() {
		List<PropertyDescriptor> descriptors =
				Arrays.stream(Introspector.getBeanInfo(_getEffectiveType(), Stringable.class).getPropertyDescriptors())
						.filter(d -> d.getReadMethod() != null)
						.filter(d -> _isCollectableWithinToString(d.getReadMethod()))
						.toList();
		Map<String,Object> propertyMap = new LinkedHashMap<>();
		for (PropertyDescriptor descriptor : descriptors) {
			Object value = descriptor.getReadMethod().invoke(this);
			if (_isCollectableWithinToString(value)) {
				propertyMap.put(descriptor.getName(), value);
			}
		}
		return _getEffectiveType().getSimpleName() + propertyMap;
	}
	
}
