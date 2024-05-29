package ua.od.whcrow.samples.spring6.boot_web.samples.web.rest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants;

import java.util.List;

@RestController
@RequestMapping(SamplesConstants.REQ_P_API_SAMPLES + "/message-source")
class MessageSourceSamplesRestController {
	
	private final MessageSource messageSource;
	
	public MessageSourceSamplesRestController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping
	public List<String> messageSource() {
		return List.of(messageSource.getMessage("app.samples.test", new Object[]{getClass()},
				LocaleContextHolder.getLocale()));
	}
	
}
