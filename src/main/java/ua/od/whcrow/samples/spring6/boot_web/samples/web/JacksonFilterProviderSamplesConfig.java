package ua.od.whcrow.samples.spring6.boot_web.samples.web;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants;

@Configuration
class JacksonFilterProviderSamplesConfig {
	
	@Bean(name = SamplesConstants.BN_SAMPLES_JACKSON_FILTER_PROVIDER)
	public FilterProvider samplesFilterProvider() {
		return new SimpleFilterProvider()
				.addFilter("NO_ID", SimpleBeanPropertyFilter.serializeAllExcept("id"));
	}
	
}
