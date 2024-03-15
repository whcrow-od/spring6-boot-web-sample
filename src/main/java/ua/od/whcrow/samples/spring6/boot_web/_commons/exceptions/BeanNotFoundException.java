package ua.od.whcrow.samples.spring6.boot_web._commons.exceptions;

import jakarta.annotation.Nonnull;
import org.springframework.core.ResolvableType;

public class BeanNotFoundException extends BeanException {
	
	private static final String MSG_WITH_NAME = "Bean with the following name is not found: {}";
	private static final String MSG_WITH_TYPE = "Bean of the following type is not found: {}";
	
	public BeanNotFoundException(@Nonnull String beanName) {
		super(MSG_WITH_NAME, beanName);
	}
	
	public BeanNotFoundException(@Nonnull Class<?> beanType) {
		super(MSG_WITH_TYPE, beanType);
	}
	
	public BeanNotFoundException(@Nonnull ResolvableType beanType) {
		super(MSG_WITH_TYPE, beanType);
	}
	
}
