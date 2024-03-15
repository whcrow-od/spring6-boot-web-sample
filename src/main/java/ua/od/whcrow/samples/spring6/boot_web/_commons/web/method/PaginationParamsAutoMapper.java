package ua.od.whcrow.samples.spring6.boot_web._commons.web.method;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ua.od.whcrow.samples.spring6.boot_web._commons.BeanFinder;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityMeta;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityMetaProvider;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.ParamUtils;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.DetailedException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.InternalServerErrorException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.ParameterException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaginationParamsAutoMapper implements HandlerMethodArgumentResolver {
	
	private final BeanFinder beanFinder;
	
	public PaginationParamsAutoMapper(@Nonnull BeanFinder beanFinder) {
		this.beanFinder = beanFinder;
	}
	
	@Override
	public boolean supportsParameter(@Nonnull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Pagination.class) &&
				(parameter.getParameterType() == Pageable.class || parameter.getParameterType() == PageRequest.class);
	}
	
	@Override
	public Object resolveArgument(@Nonnull MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			@Nonnull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory)
			throws Exception {
		Pagination pagination = parameter.getParameterAnnotation(Pagination.class);
		if (pagination == null) {
			throw new InternalServerErrorException("Failed to get the pagination settings",
					new DetailedException("Supported {} is not annotated with {}", parameter, Pagination.class));
		}
		int size = ParamUtils.getIntValue(webRequest, pagination.paramSize(), pagination.size(),
				pagination.minSize(), pagination.maxSize());
		int number = ParamUtils.getIntValue(webRequest, pagination.paramNum(), pagination.num());
		String[] sort = ParamUtils.getValues(webRequest, pagination.paramSort(), pagination.sort());
		validateSort(pagination, sort);
		Sort.Direction order = ParamUtils.getValue(webRequest, pagination.paramOrder(), pagination.order(),
				Sort.Direction::fromString);
		return sort.length == 0 ? PageRequest.of(number, size) : PageRequest.of(number, size, Sort.by(order, sort));
	}
	
	private void validateSort(@Nonnull Pagination pagination, @Nonnull String[] sort) {
		if (pagination.entity() == Void.class) {
			return;
		}
		EntityMeta entityMeta = beanFinder.getBean(EntityMetaProvider.class, pagination.entity()).getEntityMeta();
		List<String> invalidAttributes = Arrays.stream(sort)
				.filter(name -> !entityMeta.hasAttributeMeta(name))
				.toList();
		if (!invalidAttributes.isEmpty()) {
			throw new ParameterException(Collections.singletonMap(pagination.paramSort(),
					"Sorting by the following attributes is not available: " + invalidAttributes));
		}
	}
	
}
