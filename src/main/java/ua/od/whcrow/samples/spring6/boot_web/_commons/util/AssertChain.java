package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class AssertChain<T> {
	
	private final T object;
	private final String name;
	
	public AssertChain(T object, String name) {
		this.object = object;
		this.name = name;
	}
	
	public AssertChain(T object, Supplier<String> nameSupplier) {
		this(object, Assert.getName(nameSupplier));
	}
	
	@Nonnull
	public static <T> AssertChain<T> of(T object, String name) {
		return new AssertChain<>(object, name);
	}
	
	@Nonnull
	public static <T> AssertChain<T> of(T object, Supplier<String> nameSupplier) {
		return new AssertChain<>(object, nameSupplier);
	}
	
	public AssertChain<T> chain(BiFunction<T,String,T> assertFunc) {
		if (assertFunc != null) {
			assertFunc.apply(object, name);
		}
		return this;
	}
	
	public T get() {
		return object;
	}
	
}
