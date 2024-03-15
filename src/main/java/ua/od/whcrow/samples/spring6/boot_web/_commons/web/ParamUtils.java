package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.functions.x.FunctionX;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.ParameterException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.StatusException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

public final class ParamUtils {
	
	private ParamUtils()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(ParamUtils.class);
	}
	
	@Nullable
	private static <T, X extends Exception> T mapValue(@Nonnull String name, @Nonnull String value,
			@Nonnull FunctionX<String,T,X> valueMapper) {
		try {
			return valueMapper.apply(value);
		} catch (Exception e) {
			if (e instanceof StatusException statusException) {
				throw statusException;
			}
			throw new ParameterException(e, Collections.singletonMap(name, "Invalid value"));
		}
	}
	
	@Nonnull
	public static String getValueRequired(@Nonnull WebRequest request, @Nonnull String name) {
		String value = request.getParameter(name);
		if (value == null) {
			throw new ParameterException(Collections.singletonMap(name, "Required"));
		}
		return value;
	}
	
	@Nullable
	public static <T, X extends Exception> T getValue(@Nonnull WebRequest request, @Nonnull String name,
			@Nonnull FunctionX<String,T,X> valueMapper) {
		String rawValue = request.getParameter(name);
		return rawValue == null ? null : mapValue(name, rawValue, valueMapper);
	}
	
	@Nonnull
	public static <T, X extends Exception> T getValue(@Nonnull WebRequest request, @Nonnull String name,
			@Nonnull T defaultValue, @Nonnull FunctionX<String,T,X> valueMapper) {
		T value = getValue(request, name, valueMapper);
		return value == null ? defaultValue : value;
	}
	
	@Nonnull
	public static <T, X extends Exception> T getValueRequired(@Nonnull WebRequest request,
			@Nonnull String name, @Nonnull FunctionX<String,T,X> valueMapper) {
		String rawValue = getValueRequired(request, name);
		T value = mapValue(name, rawValue, valueMapper);
		if (value == null) {
			throw new ParameterException(Collections.singletonMap(name, "Invalid value"));
		}
		return value;
	}
	
	@Nonnull
	public static String[] getValues(@Nonnull WebRequest request, @Nonnull String name,
			@Nonnull String[] defaultValue) {
		String[] value = request.getParameterValues(name);
		return value == null || value.length == 0 ? defaultValue : value;
	}
	
	@Nullable
	public static Integer getIntValue(@Nonnull WebRequest request, @Nonnull String name) {
		String rawValue = request.getParameter(name);
		return rawValue == null ? null : mapValue(name, rawValue, Integer::parseInt);
	}
	
	public static int getIntValue(@Nonnull WebRequest request, @Nonnull String name, int defaultValue) {
		Integer value = getIntValue(request, name);
		return value == null ? defaultValue : value;
	}
	
	public static int getIntValue(@Nonnull WebRequest request, @Nonnull String name, int defaultValue,
			@Nullable Integer minValue, @Nullable Integer maxValue) {
		int value = getIntValue(request, name, defaultValue);
		if (minValue != null && value < minValue) {
			return minValue;
		}
		if (maxValue != null && value > maxValue) {
			return maxValue;
		}
		return value;
	}
	
	public static int getIntValueRequired(@Nonnull WebRequest request, @Nonnull String name) {
		Integer value = getIntValue(request, name);
		if (value == null) {
			throw new ParameterException(Collections.singletonMap(name, "Required number"));
		}
		return value;
	}
	
}
