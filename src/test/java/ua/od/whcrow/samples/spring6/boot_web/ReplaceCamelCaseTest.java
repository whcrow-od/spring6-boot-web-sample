package ua.od.whcrow.samples.spring6.boot_web;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class CustomDisplayNameGeneratorTest {
	
	@Order(1)
	@Nested
	class ReplaceCamelCaseTest {
		
		@Test
		void test1GenericTIn10ms100TimesWithDTOAndJWT() {
		}
		
		@Test
		void testGenericTIn10ms100Times() {
		}
		
		@ParameterizedTest(name = "Complete in {0} cycles")
		@ValueSource(ints = {10, 100, 1000})
		void genericTIn10ms100(int number) {
		}
		
	}
	
	@Order(2)
	@Nested
	//@DisplayNameGeneration(IndicativeSentences.class)
	class IndicativeSentencesTest {
		
		@Test
		void test1GenericTIn10ms100TimesWithDTOAndJWT() {
		}
		
		@Test
		void testGenericTIn10ms100Times() {
		}
		
		@ParameterizedTest(name = "Complete in {0} cycles")
		@ValueSource(ints = {10, 100, 1000})
		void genericTIn10ms100(int number) {
		}
		
	}
	
}