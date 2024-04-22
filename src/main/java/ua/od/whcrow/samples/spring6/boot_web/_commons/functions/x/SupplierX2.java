package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface SupplierX2<T, X1 extends Throwable, X2 extends Throwable> {
	
	T get()
			throws X1, X2;
	
}
