package ua.od.whcrow.samples.spring6.boot_web.samples.web.rest;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.CsrfIgnore;
import ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants;

import static ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants.BN_SAMPLES_JACKSON_FILTER_PROVIDER;

@RestController
@RequestMapping(JacksonSamplesRestController.REQUEST_PATH)
@CsrfIgnore
class JacksonSamplesRestController {
	
	static final String REQUEST_PATH = SamplesConstants.REQ_P_API_SAMPLES + "/jackson";
	
	private final FilterProvider filterProvider;
	
	public JacksonSamplesRestController(@Qualifier(BN_SAMPLES_JACKSON_FILTER_PROVIDER) FilterProvider filterProvider) {
		this.filterProvider = filterProvider;
	}
	
	@GetMapping("/mapping-jackson-value")
	public MappingJacksonValue mappingJacksonValue() {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(new Person(100, "John", "Doe"));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
	
	@JsonFilter("NO_ID")
	private record Person(int id, String firstName, String lastName) {}
	
}
