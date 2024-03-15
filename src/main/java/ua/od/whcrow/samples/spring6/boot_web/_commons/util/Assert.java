package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

import java.util.Collection;
import java.util.function.Supplier;

public final class Assert {
	
	private static final String MSG_STATE = "{} state is inappropriate";
	private static final String MSG_IS_TRUE = "{} is not TRUE";
	private static final String MSG_IS_NULL = "{} must be NULL";
	private static final String MSG_NOT_NULL = "{} must not be NULL";
	private static final String MSG_NOT_EMPTY_ARRAY = "{} array must not be NULL and must contain at least one element";
	private static final String MSG_NO_NULL_ELEMENTS_ARRAY = "{} array must contain non-NULL elements only";
	private static final String MSG_NOT_EMPTY_COLLECTION =
			"{} collection must not be NULL and must contain at least one item";
	private static final String MSG_NO_NULL_ELEMENTS_COLLECTION = "{} collection must contain non-NULL items only";
	private static final String MSG_NOT_EMPTY_STR = "{} string must not be NULL or empty";
	private static final String MSG_NOT_BLANK_STR = "{} string must not be NULL or blank";
	private static final String MSG_IS_NULL_OR_NOT_EMPTY_STR = "{} string must  be NULL or not-empty";
	private static final String MSG_IS_NULL_OR_NOT_BLANK_STR = "{} string must be NULL or not-blank";
	
	private Assert()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(Assert.class);
	}
	
	@Nullable
	static String getName(@Nullable Supplier<String> nameSupplier) {
		return nameSupplier == null ? null : nameSupplier.get();
	}
	
	@Nonnull
	public static <T> AssertChain<T> chain(T object, String name) {
		return new AssertChain<>(object, name);
	}
	
	public static boolean state(boolean expression, String name) {
		org.springframework.util.Assert.state(expression, () -> Msg.format(MSG_STATE, name));
		return expression;
	}
	
	public static boolean state(boolean expression, Supplier<String> nameSupplier) {
		return state(expression, getName(nameSupplier));
	}
	
	public static boolean isTrue(boolean expression, String name) {
		org.springframework.util.Assert.state(expression, () -> Msg.format(MSG_IS_TRUE, name));
		return expression;
	}
	
	public static boolean isTrue(boolean expression, Supplier<String> nameSupplier) {
		return isTrue(expression, getName(nameSupplier));
	}
	
	@Nullable
	public static <T> T isNull(@Nullable T object, String name) {
		org.springframework.util.Assert.isNull(object, () -> Msg.format(MSG_IS_NULL, name));
		return object;
	}
	
	@Nullable
	public static <T> T isNull(@Nullable T object, Supplier<String> nameSupplier) {
		return isNull(object, getName(nameSupplier));
	}
	
	@Nonnull
	public static <T> T notNull(@Nullable T object, String name) {
		org.springframework.util.Assert.notNull(object, () -> Msg.format(MSG_NOT_NULL, name));
		return object;
	}
	
	@Nonnull
	public static <T> T notNull(@Nullable T object, Supplier<String> nameSupplier) {
		return notNull(object, getName(nameSupplier));
	}
	
	@Nonnull
	public static <T> T[] notEmpty(@Nullable T[] array, String name) {
		org.springframework.util.Assert.notEmpty(array, () -> Msg.format(MSG_NOT_EMPTY_ARRAY, name));
		return array;
	}
	
	@Nonnull
	public static <T> T[] notEmpty(@Nullable T[] array, Supplier<String> nameSupplier) {
		return notEmpty(array, getName(nameSupplier));
	}
	
	@Nonnull
	public static <E, T extends Collection<E>> T notEmpty(@Nullable T collection, String name) {
		org.springframework.util.Assert.notEmpty(collection, () -> Msg.format(MSG_NOT_EMPTY_COLLECTION, name));
		return collection;
	}
	
	@Nonnull
	public static <E, T extends Collection<E>> T notEmpty(@Nullable T collection, Supplier<String> nameSupplier) {
		return notEmpty(collection, getName(nameSupplier));
	}
	
	public static <T> T[] noNullElements(@Nullable T[] array, String name) {
		org.springframework.util.Assert.noNullElements(array, () -> Msg.format(MSG_NO_NULL_ELEMENTS_ARRAY, name));
		return array;
	}
	
	public static <T> T[] noNullElements(@Nullable T[] array, Supplier<String> nameSupplier) {
		return noNullElements(array, getName(nameSupplier));
	}
	
	public static <E, T extends Collection<E>> T noNullElements(@Nullable T collection, String name) {
		org.springframework.util.Assert.noNullElements(collection,
				() -> Msg.format(MSG_NO_NULL_ELEMENTS_COLLECTION, name));
		return collection;
	}
	
	public static <E, T extends Collection<E>> T noNullElements(@Nullable T collection, Supplier<String> nameSupplier) {
		return noNullElements(collection, getName(nameSupplier));
	}
	
	@Nonnull
	public static String notEmpty(@Nullable String string, String name) {
		org.springframework.util.Assert.hasLength(string, () -> Msg.format(MSG_NOT_EMPTY_STR, name));
		return string;
	}
	
	@Nonnull
	public static String notEmpty(@Nullable String string, Supplier<String> nameSupplier) {
		return notEmpty(string, getName(nameSupplier));
	}
	
	@Nonnull
	public static String notBlank(@Nullable String string, String name) {
		org.springframework.util.Assert.hasLength(string == null ? null : string.trim(),
				() -> Msg.format(MSG_NOT_BLANK_STR, name));
		return string;
	}
	
	@Nonnull
	public static String notBlank(@Nullable String string, Supplier<String> nameSupplier) {
		return notBlank(string, getName(nameSupplier));
	}
	
	@Nullable
	public static String isNullOrNotEmpty(@Nullable String string, String name) {
		if (string == null) {
			return null;
		}
		org.springframework.util.Assert.hasLength(string, () -> Msg.format(MSG_IS_NULL_OR_NOT_EMPTY_STR, name));
		return string;
	}
	
	@Nullable
	public static String isNullOrNotEmpty(@Nullable String string, Supplier<String> nameSupplier) {
		return isNullOrNotEmpty(string, getName(nameSupplier));
	}
	
	@Nullable
	public static String isNullOrNotBlank(@Nullable String string, String name) {
		if (string == null) {
			return null;
		}
		org.springframework.util.Assert.hasLength(string.trim(), () -> Msg.format(MSG_IS_NULL_OR_NOT_BLANK_STR, name));
		return string;
	}
	
	@Nullable
	public static String isNullOrNotBlank(@Nullable String string, Supplier<String> nameSupplier) {
		return isNullOrNotBlank(string, getName(nameSupplier));
	}
	
	public static String[] noEmptyElements(@Nullable String[] array, String name) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				notEmpty(array[i], name + " array element #" + i);
			}
		}
		return array;
	}
	
	public static String[] noEmptyElements(@Nullable String[] array, Supplier<String> nameSupplier) {
		return noEmptyElements(array, getName(nameSupplier));
	}
	
	public static String[] noBlankElements(@Nullable String[] array, String name) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				notBlank(array[i], name + " array element #" + i);
			}
		}
		return array;
	}
	
	public static String[] noBlankElements(@Nullable String[] array, Supplier<String> nameSupplier) {
		return noBlankElements(array, getName(nameSupplier));
	}
	
}
