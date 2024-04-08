package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.od.whcrow.samples.spring6.boot_web._commons.BeanFinder;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.ElectiveMappers;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.method.BodyDtoAutoMapper;

import java.util.List;

@Configuration
class BodyDtoAutoMapperConfig implements WebMvcConfigurer {
	
	private final ElectiveMappers mappers;
	private final BeanFinder beanFinder;
	
	private List<HttpMessageConverter<?>> converters;
	
	public BodyDtoAutoMapperConfig(ElectiveMappers mappers, BeanFinder beanFinder) {
		this.mappers = mappers;
		this.beanFinder = beanFinder;
	}
	
	@Override
	public void extendMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
		this.converters = converters;
	}
	
	@Bean
	public BodyDtoAutoMapper bodyDtoAutoMapper() {
		return new BodyDtoAutoMapper(converters, mappers, beanFinder);
	}
	
}
