package ua.od.whcrow.samples.spring6.boot_web._commons.validation;

import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;

import java.lang.annotation.Annotation;

public abstract class AbstractConstraintValidation<A extends Annotation, T> implements ConstraintValidator<A,T> {
	
	private A annotation;
	
	@Override
	public void initialize(@Nonnull A annotation) {
		this.annotation = annotation;
	}
	
	@Nonnull
	protected A getAnnotation() {
		return annotation;
	}
	
}
