package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FaultyCodeException;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ConstantsUtil {
	
	private ConstantsUtil()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(ConstantsUtil.class);
	}
	
	@Nonnull
	public static Stream<Field> getConstantFieldStream(@Nonnull Class<?> constantsClass, boolean declaredOnly) {
		return Arrays.stream(declaredOnly ? constantsClass.getDeclaredFields() : constantsClass.getFields())
				.filter(field -> Modifier.isPublic(field.getModifiers()))
				.filter(field -> Modifier.isStatic(field.getModifiers()))
				.filter(field -> Modifier.isFinal(field.getModifiers()));
	}
	
	@Nonnull
	public static Map<Field,Object> getConstants(@Nonnull Class<?> constantsClass, boolean declaredOnly) {
		return getConstantFieldStream(constantsClass, declaredOnly).collect(Collectors.toMap(f -> f, f -> {
			try {
				return f.get(null);
			} catch (IllegalAccessException e) {
				throw new FaultyCodeException(
						"Something wrong with constant fields collecting, only accessible fields must be obtained", e);
			}
		}));
	}
	
	@Nonnull
	private static Map<Field,Object> _getConstants(@Nonnull Class<?> constantsClass, boolean declaredOnly,
			@Nonnull ToBooleanBiFunction<Field,Object> matcher, boolean matching) {
		return getConstants(constantsClass, declaredOnly).entrySet().stream()
				.filter(entry -> matching == matcher.applyAsBoolean(entry.getKey(), entry.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	@Nonnull
	public static Map<Field,Object> getMatchingConstants(@Nonnull Class<?> constantsClass, boolean declaredOnly,
			@Nonnull ToBooleanBiFunction<Field,Object> matcher) {
		return _getConstants(constantsClass, declaredOnly, matcher, true);
	}
	
	@Nonnull
	public static Map<Field,Object> getNonMatchingConstants(@Nonnull Class<?> constantsClass, boolean declaredOnly,
			@Nonnull ToBooleanBiFunction<Field,Object> matcher) {
		return _getConstants(constantsClass, declaredOnly, matcher, false);
	}
	
}
