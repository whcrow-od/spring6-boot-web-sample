package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.BiFunctionX;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.BiFunctionX2;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.FunctionX;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.FunctionX2;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.SupplierX;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.SupplierX2;

public class Morpher<T> {
	
	private final T object;
	
	private Morpher(@Nonnull T object) {
		this.object = object;
	}
	
	@Nonnull
	public static <T> Morpher<T> of(@Nonnull T object) {
		Assert.notNull(object, "object");
		return new Morpher<>(object);
	}
	
	@Nonnull
	public static <R, X extends Throwable> Morpher<R> of(@Nonnull SupplierX<R,X> initializer)
			throws X {
		return ofX2(initializer);
	}
	
	@Nonnull
	public static <A, R, X extends Throwable> Morpher<R> of(@Nonnull A initializerArg,
			@Nonnull FunctionX<A,R,X> initializer)
			throws X {
		return ofX2(initializerArg, initializer);
	}
	
	@Nonnull
	public static <A1, A2, R, X extends Throwable> Morpher<R> of(@Nonnull A1 initializerArg1,
			@Nonnull A2 initializerArg2, @Nonnull BiFunctionX<A1,A2,R,X> initializer)
			throws X {
		return ofX2(initializerArg1, initializerArg2, initializer);
	}
	
	@Nonnull
	public static <R, X1 extends Throwable, X2 extends Throwable> Morpher<R> ofX2(
			@Nonnull SupplierX2<R,X1,X2> initializer)
			throws X1, X2 {
		Assert.notNull(initializer, "initializer");
		return of(initializer.get());
	}
	
	@Nonnull
	public static <A, R, X1 extends Throwable, X2 extends Throwable> Morpher<R> ofX2(@Nonnull A initializerArg,
			@Nonnull FunctionX2<A,R,X1,X2> initializer)
			throws X1, X2 {
		Assert.notNull(initializerArg, "initializerArg");
		Assert.notNull(initializer, "initializer");
		return of(initializer.apply(initializerArg));
	}
	
	@Nonnull
	public static <A1, A2, R, X1 extends Throwable, X2 extends Throwable> Morpher<R> ofX2(@Nonnull A1 initializerArg1,
			@Nonnull A2 initializerArg2, @Nonnull BiFunctionX2<A1,A2,R,X1,X2> initializer)
			throws X1, X2 {
		Assert.notNull(initializerArg1, "initializerArg1");
		Assert.notNull(initializerArg2, "initializerArg2");
		Assert.notNull(initializer, "initializer");
		return of(initializer.apply(initializerArg1, initializerArg2));
	}
	
	@Nonnull
	public <R, X extends Throwable> Morpher<R> morph(@Nonnull FunctionX<T,R,X> morphingFunction)
			throws X {
		return of(result(), morphingFunction);
	}
	
	@Nonnull
	public <A2, R, X extends Throwable> Morpher<R> morphArg1(@Nonnull A2 morphingArg2,
			@Nonnull BiFunctionX<T,A2,R,X> morphingFunction)
			throws X {
		return of(result(), morphingArg2, morphingFunction);
	}
	
	@Nonnull
	public <A1, R, X extends Throwable> Morpher<R> morphArg2(@Nonnull A1 morphingArg1,
			@Nonnull BiFunctionX<A1,T,R,X> morphingFunction)
			throws X {
		return of(morphingArg1, result(), morphingFunction);
	}
	
	@Nonnull
	public <R, X1 extends Throwable, X2 extends Throwable> Morpher<R> morphX2(
			@Nonnull FunctionX2<T,R,X1,X2> morphingFunction)
			throws X1, X2 {
		return ofX2(result(), morphingFunction);
	}
	
	@Nonnull
	public <A2, R, X1 extends Throwable, X2 extends Throwable> Morpher<R> morphArg1X2(@Nonnull A2 morphingArg2,
			@Nonnull BiFunctionX2<T,A2,R,X1,X2> morphingFunction)
			throws X1, X2 {
		return ofX2(result(), morphingArg2, morphingFunction);
	}
	
	@Nonnull
	public <A1, R, X1 extends Throwable, X2 extends Throwable> Morpher<R> morphArg2X2(@Nonnull A1 morphingArg1,
			@Nonnull BiFunctionX2<A1,T,R,X1,X2> morphingFunction)
			throws X1, X2 {
		return ofX2(morphingArg1, result(), morphingFunction);
	}
	
	@Nonnull
	public T result() {
		return object;
	}
	
}
