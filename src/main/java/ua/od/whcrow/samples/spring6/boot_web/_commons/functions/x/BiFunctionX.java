package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface BiFunctionX<T, U, R, X extends Throwable> extends BiFunctionX2<T,U,R,X,RuntimeException> {
	
	@Override
	R apply(T t, U u)
			throws X;
	
}
