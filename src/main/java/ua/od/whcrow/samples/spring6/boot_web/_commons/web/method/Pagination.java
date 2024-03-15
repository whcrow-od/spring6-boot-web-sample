package ua.od.whcrow.samples.spring6.boot_web._commons.web.method;

import org.springframework.data.domain.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {
	
	Class<?> entity() default Void.class;
	
	int size() default 10;
	int num() default 0;
	String[] sort() default {};
	Sort.Direction order() default Sort.Direction.ASC;
	
	int minSize() default 5;
	int maxSize() default 100;
	
	String paramSize() default "page-size";
	String paramNum() default "page-number";
	String paramSort() default "sort-by";
	String paramOrder() default "sort-order";
	
}
