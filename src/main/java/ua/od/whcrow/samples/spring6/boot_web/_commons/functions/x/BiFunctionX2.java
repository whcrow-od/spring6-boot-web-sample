package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface BiFunctionX2<T, U, R, X1 extends Throwable, X2 extends Throwable> {
	
	R apply(T t, U u)
			throws X1, X2;
	
}
