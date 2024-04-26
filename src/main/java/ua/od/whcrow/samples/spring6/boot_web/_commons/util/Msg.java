package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class Msg {
	
	public static final String DELIMITER = "{}";
	
	private Msg()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(Msg.class);
	}
	
	@Nonnull
	private static List<Object> _convertArrayObjectToList(@Nonnull Object arrayObject) {
		if (!arrayObject.getClass().getComponentType().isPrimitive()) {
			return Arrays.asList((Object[]) arrayObject);
		}
		int length = Array.getLength(arrayObject);
		Object[] array = new Object[length];
		for (int i = 0; i < length; i++) {
			array[i] = Array.get(arrayObject, i);
		}
		return Arrays.asList(array);
	}
	
	@Nonnull
	public static String stringify(@Nullable Object o) {
		if (o == null) {
			return "NULL";
		}
		if (o.getClass().isArray()) {
			o = _convertArrayObjectToList(o);
		}
		return o.toString();
	}
	
	// FIXME: Msg.format("{} not found", "Identity") => "Identity" instead of "Identity not found"
	@Nonnull
	public static String format(@Nonnull Function<Object,String> stringifier, @Nonnull String delimiter,
			@Nonnull String msg, Object... args) {
		Assert.notNull(stringifier, "stringifier");
		Assert.notBlank(delimiter, "delimiter");
		Assert.notNull(msg, "msg");
		StringBuilder builder = new StringBuilder(msg.length() + (args.length * 32));
		for (int argIndex = 0, delimiterIndex, textIndex = 0; argIndex < args.length; argIndex++) {
			delimiterIndex = msg.indexOf(delimiter, textIndex);
			if (delimiterIndex == -1) {
				if (textIndex == 0) {
					return msg;
				}
				return builder.append(msg, textIndex, msg.length()).toString();
			}
			builder.append(msg, textIndex, delimiterIndex);
			String strArg;
			try {
				strArg = stringifier.apply(args[argIndex]);
			} catch (Exception e) {
				strArg = "!STRINGIFIER-EXCEPTION " + e + "!";
			}
			builder.append(strArg);
			textIndex = delimiterIndex + delimiter.length();
		}
		return builder.toString();
	}
	
	@Nonnull
	public static String format(@Nonnull Function<Object,String> stringifier, @Nonnull String msg, Object... args) {
		return format(stringifier, DELIMITER, msg, args);
	}
	
	@Nonnull
	public static String format(@Nonnull String msg, Object... args) {
		return format(Msg::stringify, msg, args);
	}
	
}
