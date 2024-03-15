package ua.od.whcrow.samples.spring6.boot_web._commons;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.function.Predicate;

public final class StrUtils {
	
	private StrUtils()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(StrUtils.class);
	}
	
	public static boolean isAllMatch(@Nullable CharSequence charSequence, @Nonnull Predicate<Character> predicate) {
		if (charSequence == null) {
			return false;
		}
		int length = charSequence.length();
		for (int i = 0; i < length; i++) {
			if (!predicate.test(charSequence.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isUppercase(@Nullable CharSequence charSequence, boolean ignoreNonAlphabetic) {
		return isAllMatch(charSequence,
				c -> Character.isUpperCase(c) || (ignoreNonAlphabetic && !Character.isAlphabetic(c)));
	}
	
	public static boolean isUppercase(@Nullable CharSequence charSequence) {
		return isUppercase(charSequence, false);
	}
	
}
