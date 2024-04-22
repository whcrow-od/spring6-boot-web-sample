package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface PredicateX<T, X extends Throwable> extends PredicateX2<T,X,RuntimeException> {
	
	@Override
	boolean test(T t)
			throws X;
	
}
