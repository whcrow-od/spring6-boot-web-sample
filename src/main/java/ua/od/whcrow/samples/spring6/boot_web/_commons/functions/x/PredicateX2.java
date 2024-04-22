package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface PredicateX2<T, X1 extends Throwable, X2 extends Throwable> {
	
	boolean test(T t)
			throws X1, X2;
	
}
