package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class Msg {
	
	public static final Function<Object,String> STRINGIFIER = Msg::stringify;
	public static final String DELIMITER = "{}";
	public static final String NULL = String.valueOf((Object) null);
	
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
			return NULL;
		}
		if (o.getClass().isArray()) {
			o = _convertArrayObjectToList(o);
		}
		return o.toString();
	}
	
	@Nonnull
	public static String format(@Nonnull Function<Object,String> stringifier, @Nonnull String delimiter,
			@Nonnull String msg, Object... args) {
		Assert.notNull(stringifier, "stringifier");
		Assert.notBlank(delimiter, "delimiter");
		Assert.notNull(msg, "msg");
		StringBuilder builder = new StringBuilder(msg.length() + (args.length * 32));
		int argIndex = 0, delimiterIndex, textIndex = 0;
		while ((delimiterIndex = msg.indexOf(delimiter, textIndex)) != -1) {
			builder.append(msg, textIndex, delimiterIndex);
			if (argIndex < args.length) {
				String strArg;
				try {
					strArg = stringifier.apply(args[argIndex++]);
				} catch (Exception e) {
					strArg = "!STRINGIFIER-EXCEPTION " + e + "!";
				}
				builder.append(strArg);
			}
			textIndex = delimiterIndex + delimiter.length();
		}
		if (textIndex == 0) {
			return msg;
		}
		builder.append(msg, textIndex, msg.length());
		return builder.toString();
	}
	
	@Nonnull
	public static String format(@Nonnull String msg, Object... args) {
		return format(STRINGIFIER, DELIMITER, msg, args);
	}
	
}
