package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.BiFunctionX;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.FunctionX;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.SupplierX;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Morpher;

/**
 * Data processing shortcuts are designed to simplify common data flow in controller methods, e.g. creating of new
 * domain model object: <ol><li>map a request body DTO to new domain model object</li><li>save the domain model
 * object</li><li>map the saved domain model object to response body DTO</li></ol>
 */
public final class DPShortcut {
	
	private DPShortcut()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(DPShortcut.class);
	}
	
	@Nonnull
	public static <IA, M, IX extends Throwable, SX extends Throwable, R, MX extends Throwable> R create(
			@Nonnull IA initializerArg, @Nonnull FunctionX<IA,M,IX> initializer, @Nonnull FunctionX<M,M,SX> saver,
			@Nonnull FunctionX<M,R,MX> mapper)
			throws IX, SX, MX {
		return Morpher.of(initializerArg, initializer)
				.morph(saver)
				.morph(mapper)
				.result();
	}
	
	@Nonnull
	public static <IA, M, IX extends Throwable, R, MX extends Throwable> R read(@Nonnull IA initializerArg,
			@Nonnull FunctionX<IA,M,IX> initializer, @Nonnull FunctionX<M,R,MX> mapper)
			throws IX, MX {
		return Morpher.of(initializerArg, initializer)
				.morph(mapper)
				.result();
	}
	
	@Nonnull
	public static <M, IX extends Throwable, R, MX extends Throwable> R read(@Nonnull SupplierX<M,IX> initializer,
			@Nonnull FunctionX<M,R,MX> mapper)
			throws IX, MX {
		return Morpher.of(initializer)
				.morph(mapper)
				.result();
	}
	
	@Nonnull
	public static <IA, M, IX extends Throwable, UA, UX extends Throwable, SX extends Throwable, R, MX extends Throwable> R update(
			@Nonnull IA initializerArg, @Nonnull FunctionX<IA,M,IX> initializer, @Nonnull UA updaterArg,
			@Nonnull BiFunctionX<UA,M,M,UX> updater, @Nonnull FunctionX<M,M,SX> saver,
			@Nonnull FunctionX<M,R,MX> mapper)
			throws IX, UX, SX, MX {
		return Morpher.of(initializerArg, initializer)
				.morphArg2(updaterArg, updater)
				.morph(saver)
				.morph(mapper)
				.result();
	}
	
}
