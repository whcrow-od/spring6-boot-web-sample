package ua.od.whcrow.samples.spring6.boot_web;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.platform.commons.util.Preconditions;

import java.lang.reflect.Array;

public class VarargsAggregator implements ArgumentsAggregator {
	
	@Override
	public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
			throws ArgumentsAggregationException {
		Class<?> parameterType = context.getParameter().getType();
		Preconditions.condition(parameterType.isArray(), () -> "parameter must be an array");
		Object array = Array.newInstance(parameterType.getComponentType(), accessor.size() - context.getIndex());
		for (int argIndex = context.getIndex(), arrIndex = 0; argIndex < accessor.size(); argIndex++, arrIndex++) {
			Array.set(array, arrIndex, accessor.get(argIndex, parameterType.getComponentType()));
		}
		return array;
	}
	
}
