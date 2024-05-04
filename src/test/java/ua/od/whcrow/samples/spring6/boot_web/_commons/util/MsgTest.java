package ua.od.whcrow.samples.spring6.boot_web._commons.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.od.whcrow.samples.spring6.boot_web.VarargsAggregator;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MsgTest {
	
	private static Stream<Arguments> testFormat() {
		int[] primArr = new int[]{0, 1, 2, 3};
		Boolean[] objArr = new Boolean[]{true, false, null};
		Class<?> type = Object.class;
		Map<?,?> map = Map.of("key0", 100);
		return Stream.of(
				Arguments.of("No args, no placeholders",
						"No args, no placeholders"),
				Arguments.of("No args, with one placeholder",
						"No args, with one placeholder" + Msg.DELIMITER),
				Arguments.of("No args, with placeholders",
						"No args" + Msg.DELIMITER + ", with placeholders" + Msg.DELIMITER),
				Arguments.of("No placeholders, with one arg",
						"No placeholders, with one arg",
						"arg0"),
				Arguments.of("No placeholders, with two args",
						"No placeholders, with two args",
						"arg0", "arg1"),
				Arguments.of("Placeholder is the first word",
						Msg.DELIMITER + " is the first word",
						"Placeholder"),
				Arguments.of("Last word is placeholder",
						"Last word is " + Msg.DELIMITER,
						"placeholder"),
				Arguments.of("Some placeholder in the middle",
						"Some " + Msg.DELIMITER + " in the middle",
						"placeholder"),
				Arguments.of("Test an array of primitives as arg: " + Arrays.stream(primArr).boxed().toList(),
						"Test an array of primitives as arg: " + Msg.DELIMITER,
						primArr),
				Arguments.of("Test an array of objects as arg: " + Arrays.asList(objArr),
						"Test an array of objects as arg: " + Msg.DELIMITER,
						objArr),
				Arguments.of("Test a " + Msg.NULL + " arg",
						"Test a " + Msg.DELIMITER + " arg",
						null),
				Arguments.of("Complex arg0: " + type + "; and another complex arg1: " + map,
						"Complex arg0: " + Msg.DELIMITER + "; and another complex arg1: " + Msg.DELIMITER,
						type, map)
		);
	}
	
	@ParameterizedTest
	@MethodSource
	void testFormat(String expected, String message, @AggregateWith(VarargsAggregator.class) Object... args) {
		String actual = Msg.format(message, args);
		assertEquals(expected, actual);
	}
	
}