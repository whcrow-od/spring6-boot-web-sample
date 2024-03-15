package ua.od.whcrow.samples.spring6.boot_web._commons;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.BeanNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.Optional;

@Lazy
@Component
class BeanFinderImpl implements BeanFinder {
	
	@Value(Constants.AP_VP_COMMONS_BEAN_FINDER_EAGER)
	private boolean eagerByDefault;
	
	private final ListableBeanFactory beanFactory;
	
	public BeanFinderImpl(@Nonnull ListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	@Override
	public boolean isEagerByDefault() {
		return eagerByDefault;
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public <T> Optional<T> findBean(@Nonnull ResolvableType beanType, boolean eager) {
		Assert.notNull(beanType, "resolvable bean type");
		return Optional.ofNullable((T) beanFactory.getBeanProvider(beanType, eager).getIfAvailable());
	}
	
	@Nonnull
	@Override
	public <T> Optional<T> findBean(@Nonnull Class<T> beanClass, boolean eager) {
		Assert.notNull(beanClass, "bean class");
		return Optional.ofNullable(beanFactory.getBeanProvider(beanClass, eager).getIfAvailable());
	}
	
	@Nonnull
	@Override
	public <T> Optional<T> findBean(@Nonnull Class<T> beanClass, boolean eager, Class<?>... generics) {
		Assert.notNull(beanClass, "bean class");
		Assert.notNull(generics, "bean class generics");
		return findBean(ResolvableType.forClassWithGenerics(beanClass, generics), eager);
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public <T> T getBean(@Nonnull ResolvableType beanType, boolean eager)
			throws BeanNotFoundException {
		return (T) findBean(beanType, eager).orElseThrow(() -> new BeanNotFoundException(beanType));
	}
	
	@Nonnull
	@Override
	public <T> T getBean(@Nonnull Class<T> beanClass, boolean eager)
			throws BeanNotFoundException {
		return findBean(beanClass, eager).orElseThrow(() -> new BeanNotFoundException(beanClass));
	}
	
	@Nonnull
	@Override
	public <T> T getBean(@Nonnull Class<T> beanClass, boolean eager, Class<?>... generics)
			throws BeanNotFoundException {
		return getBean(ResolvableType.forClassWithGenerics(beanClass, generics), eager);
	}
	
}
