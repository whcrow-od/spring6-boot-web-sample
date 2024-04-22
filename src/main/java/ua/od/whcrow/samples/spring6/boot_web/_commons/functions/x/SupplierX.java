package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface SupplierX<T, X extends Throwable> extends SupplierX2<T,X,RuntimeException> {
	
	@Override
	T get()
			throws X;
	
}
