package ua.od.whcrow.samples.spring6.boot_web._commons.web.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BodyDto {
	
	Class<?> dtoType();
	
}
