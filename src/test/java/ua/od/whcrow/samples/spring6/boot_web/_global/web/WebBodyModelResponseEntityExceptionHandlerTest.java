package ua.od.whcrow.samples.spring6.boot_web._global.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.od.whcrow.samples.spring6.boot_web.ApplicationTest;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ApplicationTest
class WebBodyModelResponseEntityExceptionHandlerTest {
	
	@Autowired
	private ResponseEntityExceptionHandler responseEntityExceptionHandler;
	
	@Autowired
	private WebExceptionHandler handler;
	
	@Test
	void checkWhetherResponseEntityExceptionHandlerIsSubject() {
		assertEquals(responseEntityExceptionHandler, handler,
				() -> Msg.format("Autowired {} must be equal to autowired {}",
						ResponseEntityExceptionHandler.class.getSimpleName(),
						WebExceptionHandler.class.getName()));
	}
	
	/*@Nested
	class HandleExceptionStatusException {
		
		@Test
		void shouldThrowExceptionIfArgIsNull() {
			assertThrows(IllegalArgumentException.class, () -> handler.handleException((StatusException) null));
		}
		
	}
	
	@Nested
	class HandleExceptionParameterException {
		
		@Test
		void shouldThrowExceptionIfArgIsNull() {
			assertThrows(IllegalArgumentException.class, () -> handler.handleException((ParameterException) null));
		}
		
	}*/
	
}