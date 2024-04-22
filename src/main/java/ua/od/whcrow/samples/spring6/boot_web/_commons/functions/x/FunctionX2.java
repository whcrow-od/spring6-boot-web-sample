package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface FunctionX2<T, R, X1 extends Throwable, X2 extends Throwable> {
	
	R apply(T t)
			throws X1, X2;
	
}
