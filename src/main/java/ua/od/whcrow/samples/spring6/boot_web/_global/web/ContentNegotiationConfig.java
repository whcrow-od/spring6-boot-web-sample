package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class ContentNegotiationConfig implements WebMvcConfigurer {
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.strategies(List.of(new PrioritizedJsonForApiStrategy()));
	}
	
	private static class PrioritizedJsonForApiStrategy extends HeaderContentNegotiationStrategy {
		
		@Nonnull
		@Override
		public List<MediaType> resolveMediaTypes(@Nonnull NativeWebRequest request)
				throws HttpMediaTypeNotAcceptableException {
			List<MediaType> mediaTypes = super.resolveMediaTypes(request);
			if (WebUtils.isApiRequest(request)) {
				if (mediaTypes.stream().anyMatch(mt -> mt.includes(MediaType.APPLICATION_JSON))) {
					return List.of(MediaType.APPLICATION_JSON);
				}
			}
			return mediaTypes;
		}
		
	}
	
}
