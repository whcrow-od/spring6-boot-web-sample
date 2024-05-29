package ua.od.whcrow.samples.spring6.boot_web._commons.collections;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@TestMethodOrder(MethodOrderer.DisplayName.class)
//@DisplayNameGeneration(ReplaceCamelCase.class)
class FixedSizeHashMapTest {
	
	@Test
	void testConstructingWithNegativeSizeLimit() {
		assertThrows(IllegalArgumentException.class, () -> new FixedSizeHashMap<>(-1),
				"negative size limit must lead to exception");
	}
	
	@Test
	void testConstructingWithExceedingSizeMap() {
		int sizeLimit = 5;
		int extra = 3;
		var expectedMap = new LinkedHashMap<Integer,Integer>(sizeLimit);
		for (int i = extra; i < sizeLimit + extra; i++) {
			expectedMap.put(i, i);
		}
		var argMap = new FixedSizeHashMap<Integer,Integer>(sizeLimit);
		for (int i = 0; i < sizeLimit + extra; i++) {
			argMap.put(i, i);
		}
		var actualMap = new FixedSizeHashMap<>(sizeLimit, argMap);
		assertIterableEquals(actualMap.keySet(), expectedMap.keySet(),
				"eldest entry must be removed when specified map size is greater than limit");
	}
	
	@Test
	void testEldestEntryRemovedWhenSizeLimitReached() {
		int sizeLimit = 5;
		int extra = 3;
		var expectedMap = new LinkedHashMap<Integer,Integer>(sizeLimit);
		for (int i = extra; i < sizeLimit + extra; i++) {
			expectedMap.put(i, i);
		}
		var actualMap = new FixedSizeHashMap<Integer,Integer>(sizeLimit);
		for (int i = 0; i < sizeLimit + extra; i++) {
			actualMap.put(i, i);
		}
		assertIterableEquals(actualMap.keySet(), expectedMap.keySet(),
				"eldest entry must be removed when size limit is reached");
	}
	
}