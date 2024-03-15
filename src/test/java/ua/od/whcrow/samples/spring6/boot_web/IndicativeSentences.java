package ua.od.whcrow.samples.spring6.boot_web;

import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;

public class IndicativeSentences extends ReplaceCamelCase {
	
	@Nonnull
	@Override
	public String generateDisplayNameForNestedClass(@Nonnull Class<?> nestedClass) {
		return super.generateDisplayNameForNestedClass(nestedClass) + " ⤵";
	}
	
	@Nonnull
	@Override
	public String generateDisplayNameForMethod(@Nonnull Class<?> testClass, @Nonnull Method testMethod) {
		return testClass.getSimpleName() + " " + replaceCamelCase(testMethod.getName());
	}
	
}
