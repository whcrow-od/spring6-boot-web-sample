package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import org.slf4j.helpers.MessageFormatter;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public final class Msg {
	
	private Msg()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(Msg.class);
	}
	
	@Nonnull
	public static String format(@Nonnull String msg, Object... args) {
		Assert.notNull(msg, "message text");
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i].getClass().isArray()) {
				args[i] = wrapArrayToList(args[i]);
			}
		}
		return MessageFormatter.arrayFormat(msg, args).getMessage();
	}
	
	@Nonnull
	private static List<Object> wrapArrayToList(@Nonnull Object arrayObject) {
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
	
}
