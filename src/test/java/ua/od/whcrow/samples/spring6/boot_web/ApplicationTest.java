package ua.od.whcrow.samples.spring6.boot_web;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest
public @interface ApplicationTest {
	
	@AliasFor(annotation = SpringBootTest.class, attribute = "classes")
	Class<?>[] contextLoaders() default {Application.class, PersistedModelMockProvider.class};
	
}
