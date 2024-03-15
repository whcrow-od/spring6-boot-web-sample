package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ua.od.whcrow.samples.spring6.boot_web._commons.BeanFinder;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.method.BodyDtoAutoMapper;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.method.PaginationParamsAutoMapper;

import java.time.Duration;
import java.util.List;

@Configuration
class WebConfig extends WebMvcConfigurationSupport {
	
	private final BeanFinder beanFinder;
	
	public WebConfig(@Nonnull BeanFinder beanFinder) {
		this.beanFinder = beanFinder;
	}
	
	@Override
	protected void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/images/**")
				.addResourceLocations("classpath:/static/images/")
				.setCacheControl(CacheControl.maxAge(Duration.ofDays(28)));
		registry.addResourceHandler("/assets/js/**")
				.addResourceLocations("classpath:/static/js/")
				.setCacheControl(CacheControl.maxAge(Duration.ofDays(7)));
		registry.addResourceHandler("/assets/css/**")
				.addResourceLocations("classpath:/static/css/")
				.setCacheControl(CacheControl.maxAge(Duration.ofDays(1)));
	}
	
	@Override
	protected void addArgumentResolvers(@Nonnull List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(paginationParamsAutoMapper());
		argumentResolvers.add(bodyDtoAutoMapper());
	}
	
	@Bean
	public LayoutDialect thymeleafLayoutDialect() {
		return new LayoutDialect();
	}
	
	@Bean
	public PaginationParamsAutoMapper paginationParamsAutoMapper() {
		return new PaginationParamsAutoMapper(beanFinder);
	}
	
	@Bean
	public BodyDtoAutoMapper bodyDtoAutoMapper() {
		return new BodyDtoAutoMapper(getMessageConverters(), beanFinder);
	}
	
}
