package ua.od.whcrow.samples.spring6.boot_web._global.aspect;

import org.aspectj.lang.annotation.Pointcut;

public interface Pointcuts {
	
	// PCD (pointcut designator) "execution" expression pattern:
	// execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
	String PCD_PUBLIC_METHOD = "execution(public * *(..))";
	
	@Pointcut("within(ua.od.whcrow.samples.spring6.boot_web..*)")
	static void withinProject() {}
	
	@Pointcut("withinProject() && " + PCD_PUBLIC_METHOD)
	static void publicProjectMethod() {}
	
}
