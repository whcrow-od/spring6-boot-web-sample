package ua.od.whcrow.samples.spring6.boot_web._commons.web.method;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Deprecated because I haven't found yet how to make the springdoc-openapi generate a proper doc for it.
 * TODO: Find out how to make the springdoc-openapi generate a proper doc for it.
 */
@Deprecated
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validated
public @interface BodyDto {
	
	Class<?> value();
	
	@AliasFor(annotation = Validated.class, attribute = "value")
	Class<?>[] groups() default {};
	
}
