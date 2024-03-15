package ua.od.whcrow.samples.spring6.boot_web._commons.functions;

@FunctionalInterface
public interface ToBooleanBiFunction<T, U> {
	
	boolean applyAsBoolean(T t, U u);
	
}
