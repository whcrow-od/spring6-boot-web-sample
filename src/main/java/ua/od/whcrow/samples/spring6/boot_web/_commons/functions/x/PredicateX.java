package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface PredicateX<T, X extends Exception> {
	
	boolean test(T t)
			throws X;
	
}
