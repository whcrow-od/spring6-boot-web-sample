package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Limits access to methods with request mapping.<br/>This annotation (as potentially less restrictive) has a lower
 * priority than {@link AuthorityAccess}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneralAccess {
	
	GeneralAccessType value();
	
}
