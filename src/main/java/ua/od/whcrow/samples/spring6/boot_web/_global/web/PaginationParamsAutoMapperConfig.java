package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.od.whcrow.samples.spring6.boot_web._commons.BeanFinder;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.method.PaginationParamsAutoMapper;

import java.util.List;

@Configuration
class PaginationParamsAutoMapperConfig implements WebMvcConfigurer {
	
	private final BeanFinder beanFinder;
	
	public PaginationParamsAutoMapperConfig(@Nonnull BeanFinder beanFinder) {
		this.beanFinder = beanFinder;
	}
	
	@Override
	public void addArgumentResolvers(@Nonnull List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(paginationParamsAutoMapper());
	}
	
	@Bean
	public PaginationParamsAutoMapper paginationParamsAutoMapper() {
		return new PaginationParamsAutoMapper(beanFinder);
	}
	
}
