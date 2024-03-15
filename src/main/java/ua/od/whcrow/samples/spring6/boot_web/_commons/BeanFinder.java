package ua.od.whcrow.samples.spring6.boot_web._commons;

import jakarta.annotation.Nonnull;
import org.springframework.core.ResolvableType;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.BeanNotFoundException;

import java.util.Optional;

public interface BeanFinder {
	
	boolean isEagerByDefault();
	
	@Nonnull
	<T> Optional<T> findBean(@Nonnull ResolvableType beanType, boolean eager);
	
	@Nonnull
	<T> Optional<T> findBean(@Nonnull Class<T> beanClass, boolean eager);
	
	@Nonnull
	<T> Optional<T> findBean(@Nonnull Class<T> beanClass, boolean eager, Class<?>... generics);
	
	@Nonnull
	default <T> Optional<T> findBean(@Nonnull ResolvableType beanType) {
		return findBean(beanType, isEagerByDefault());
	}
	
	@Nonnull
	default <T> Optional<T> findBean(@Nonnull Class<T> beanClass) {
		return findBean(beanClass, isEagerByDefault());
	}
	
	@Nonnull
	default <T> Optional<T> findBean(@Nonnull Class<T> beanClass, Class<?>... generics) {
		return findBean(beanClass, isEagerByDefault(), generics);
	}
	
	@Nonnull
	<T> T getBean(@Nonnull ResolvableType beanType, boolean eager)
			throws BeanNotFoundException;
	
	@Nonnull
	<T> T getBean(@Nonnull Class<T> beanClass, boolean eager)
			throws BeanNotFoundException;
	
	@Nonnull
	<T> T getBean(@Nonnull Class<T> beanClass, boolean eager, Class<?>... generics)
			throws BeanNotFoundException;
	
	@Nonnull
	default <T> T getBean(@Nonnull ResolvableType beanType)
			throws BeanNotFoundException {
		return getBean(beanType, isEagerByDefault());
	}
	
	@Nonnull
	default <T> T getBean(@Nonnull Class<T> beanClass)
			throws BeanNotFoundException {
		return getBean(beanClass, isEagerByDefault());
	}
	
	@Nonnull
	default <T> T getBean(@Nonnull Class<T> beanClass, Class<?>... generics)
			throws BeanNotFoundException {
		return getBean(beanClass, isEagerByDefault(), generics);
	}
	
}
