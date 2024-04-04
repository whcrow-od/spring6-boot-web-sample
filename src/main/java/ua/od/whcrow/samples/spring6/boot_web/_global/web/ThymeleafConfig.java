package ua.od.whcrow.samples.spring6.boot_web._global.web;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class ThymeleafConfig implements WebMvcConfigurer {
	
	@Bean
	public LayoutDialect thymeleafLayoutDialect() {
		return new LayoutDialect();
	}
	
}
