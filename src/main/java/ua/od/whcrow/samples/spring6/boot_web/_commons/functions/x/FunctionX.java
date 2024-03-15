package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface FunctionX<T,R,X extends Exception> {
	
	R apply(T t) throws X;
	
}
