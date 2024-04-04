package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
class StaticResourceConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
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
	
}
