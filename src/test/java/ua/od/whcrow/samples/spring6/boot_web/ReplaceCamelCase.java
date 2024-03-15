package ua.od.whcrow.samples.spring6.boot_web;

import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReplaceCamelCase extends DisplayNameGenerator.Standard {
	
	private static final String DELIMITER = " ";
	private static final String[] PREFIXES = {"test", "should"};
	private static final Pattern P_PREFIXES = Pattern.compile("^(" + Arrays.stream(PREFIXES)
			.map(Pattern::quote).collect(Collectors.joining("|")) + ")([A-Z0-9])");
	private static final Pattern P_DIGITS = Pattern.compile("(\\D)([0-9]+)");
	private static final Pattern P_WORD_START = Pattern.compile("(.)([A-Z])([a-z])");
	private static final Pattern P_ABBR = Pattern.compile("([A-Z]+)");
	
	@Nonnull
	@Override
	public String generateDisplayNameForMethod(@Nonnull Class<?> testClass, @Nonnull Method testMethod) {
		String methodName = replaceCamelCase(testMethod.getName());
		if (testMethod.getParameterCount() == 0) {
			return methodName;
		}
		return methodName + " " + DisplayNameGenerator.parameterTypesAsString(testMethod);
	}
	
	@Nonnull
	protected String replaceCamelCase(@Nonnull String string) {
		string = P_PREFIXES.matcher(string).replaceFirst(match -> match.group(2));
		string = P_DIGITS.matcher(string).replaceAll("$1" + DELIMITER + "$2");
		string = P_WORD_START.matcher(string)
				.replaceAll(match -> match.group(1) + DELIMITER + match.group(2).toLowerCase() + match.group(3));
		string = P_ABBR.matcher(string).replaceAll(DELIMITER + "$1");
		return string;
	}
	
}
