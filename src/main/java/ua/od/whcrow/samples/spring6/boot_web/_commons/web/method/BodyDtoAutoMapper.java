package ua.od.whcrow.samples.spring6.boot_web._commons.web.method;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import ua.od.whcrow.samples.spring6.boot_web._commons.BeanFinder;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.ElectiveMappers;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityMetaProvider;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.ModelService;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.DetailedException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.InternalServerErrorException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Type;
import java.util.List;

// TODO: What about auto-mapping from model to DTO (for response body)
public class BodyDtoAutoMapper extends RequestResponseBodyMethodProcessor {
	
	private final ElectiveMappers mappers;
	private final BeanFinder beanFinder;
	
	public BodyDtoAutoMapper(@Nonnull List<HttpMessageConverter<?>> messageConverters, ElectiveMappers mappers,
			@Nonnull BeanFinder beanFinder) {
		// TODO: Issue with ObjectMapper serialization of Java 8 Time objects started
		//  when this call of super constructor with messageConverters was added.
		//  Java 8 Time objects are serialized like JavaTimeModule is not registered.
		super(messageConverters);
		this.mappers = mappers;
		this.beanFinder = beanFinder;
	}
	
	// Overriding this method to apply mapping only if the parameter is marked with @BodyDto
	@Override
	public boolean supportsParameter(@Nonnull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(BodyDto.class);
	}
	
	// Overriding this method to make conversion of request body to the type defined via @BodyDto annotation
	// (instead of parameter type).
	@Override
	protected Object readWithMessageConverters(@Nonnull HttpInputMessage inputMessage,
			@Nonnull MethodParameter parameter, @Nonnull Type targetType)
			throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		BodyDto bodyDTO = parameter.getParameterAnnotation(BodyDto.class);
		if (bodyDTO == null) {
			throw new InternalServerErrorException("Failed to get the body DTO settings",
					new DetailedException("Supported {} is not annotated with {}", parameter, BodyDto.class));
		}
		return super.readWithMessageConverters(inputMessage, parameter, bodyDTO.value());
	}
	
	// By default, bean is validated only if the parameter is marked with @Valid or @Validated.
	// Overriding this method to apply bean validation on all DTOs.
	@Override
	protected void validateIfApplicable(@Nonnull WebDataBinder binder, @Nonnull MethodParameter parameter) {
		binder.validate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object resolveArgument(@Nonnull MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			@Nonnull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory)
			throws Exception {
		Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		if (dto == null) {
			return null;
		}
		Object id = getId(parameter.getParameterType(), dto);
		if (id == null) {
			return mappers.create(dto, parameter.getParameterType());
		}
		Object model;
		try {
			model = beanFinder.getBean(ModelService.class, parameter.getParameterType(), id.getClass()).getById(id);
		} catch (ModelNotFoundException e) {
			throw NotFoundException.ofId(parameter.getParameterType(), id, e);
		}
		return mappers.update(dto, model);
	}
	
	@Nullable
	private Object getId(@Nonnull Class<?> modelType, @Nonnull Object dto) {
		String idAttributeName = beanFinder.getBean(EntityMetaProvider.class, modelType)
				.getIdAttributeMeta().getName();
		// Cannot use BeanInfo to call a getter method because DTO can be a record, and the records are not bean-like.
		Field dtoIdField = ReflectionUtils.findField(dto.getClass(), f -> f.getName().equals(idAttributeName));
		if (dtoIdField == null) {
			return null;
		}
		try {
			dtoIdField.setAccessible(true);
			return dtoIdField.get(dto);
		} catch (InaccessibleObjectException | SecurityException | IllegalAccessException e) {
			throw new InternalServerErrorException("Body DTO access error",
					new DetailedException("Failed to get a value from field {} of the following DTO: {}", e,
							dtoIdField, dto));
		}
	}
	
}
