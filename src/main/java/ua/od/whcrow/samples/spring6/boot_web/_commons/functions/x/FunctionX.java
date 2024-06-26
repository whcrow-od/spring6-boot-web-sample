package ua.od.whcrow.samples.spring6.boot_web._commons.functions.x;

@FunctionalInterface
public interface FunctionX<T, R, X extends Throwable> extends FunctionX2<T,R,X,RuntimeException> {
	
	@Override
	R apply(T t)
			throws X;
	
}
