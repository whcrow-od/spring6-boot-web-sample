package ua.od.whcrow.samples.spring6.boot_web._commons.validation;

import ua.od.whcrow.samples.spring6.boot_web._commons.StrUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintValidatorContext;

class UppercaseConstraintValidator extends AbstractConstraintValidation<Uppercase,String> {
	
	@Override
	public boolean isValid(@Nullable String value, @Nonnull ConstraintValidatorContext context) {
		return value == null || StrUtils.isUppercase(value, getAnnotation().ignoreNonAlphabetic());
	}
	
}
