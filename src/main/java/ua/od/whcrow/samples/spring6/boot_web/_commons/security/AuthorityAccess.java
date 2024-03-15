package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Limits access to methods with request mapping.<br/>Specifying multiple annotations of this type on the same element
 * is allowed, unlike {@link org.springframework.security.access.annotation.Secured}. The logical operator AND is used
 * for these annotations.<br/>This annotation (as potentially more restrictive) has a higher priority than
 * {@link GeneralAccess}
 */
@Repeatable(AuthorityAccessGroup.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AuthorityAccess {
	
	/**
	 * Authentication must have ANY of the specified authorities.
	 */
	String[] value();
	
}
