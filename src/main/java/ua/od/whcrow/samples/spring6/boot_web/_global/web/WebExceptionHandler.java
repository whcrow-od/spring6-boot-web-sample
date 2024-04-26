package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.BodyModel;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.BodyModelResponse;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.ViewProvider;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.WebUtils;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.ParameterException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.StatusException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.UnprocessableEntityException;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
class WebExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_FORMAT = "{} caused by {}";
	
	private final String viewNamePrefix;
	private final String defaultViewNameSuffix;
	private final ViewProvider viewProvider;
	private final WebMvcControllerAdvice webMvcControllerAdvice;
	
	public WebExceptionHandler(@Value(WebConstants.AP_VP_ERROR_VIEW_NAME_PREFIX) String viewNamePrefix,
			@Value(WebConstants.AP_VP_ERROR_DEFAULT_VIEW_NAME_SUFFIX) String defaultViewNameSuffix,
			ViewProvider viewProvider, WebMvcControllerAdvice webMvcControllerAdvice) {
		this.viewNamePrefix = viewNamePrefix;
		this.defaultViewNameSuffix = defaultViewNameSuffix;
		this.viewProvider = viewProvider;
		this.webMvcControllerAdvice = webMvcControllerAdvice;
	}
	
	private boolean isApiRequest(@Nonnull HttpServletRequest request) {
		// TODO: There should be better way to determine API request... or not
		return WebUtils.isApiRequest(request) || request.getRequestURI().startsWith(WebConstants.REQ_P_API_ROOT);
	}
	
	private void log(@Nonnull BodyModel<?> bodyModel, @Nonnull Exception exception) {
		if (bodyModel.getStatus().is5xxServerError()) {
			logger.error(bodyModel.toString(), exception);
			return;
		}
		logger.warn(Msg.format(MSG_FORMAT, bodyModel, exception));
	}
	
	@Nonnull
	private String getErrorViewName(@Nonnull String viewNameSuffix) {
		return viewNamePrefix + viewNameSuffix;
	}
	
	@Nonnull
	private BodyModel<?> buildBodyModel(@Nonnull StatusException exception, @Nonnull HttpServletRequest request) {
		Object payload = null;
		if (exception instanceof ParameterException parameterException) {
			payload = parameterException.getParameters();
		} else if (exception instanceof UnprocessableEntityException unprocessableEntityException) {
			payload = unprocessableEntityException.getParameters();
		}
		BodyModel<?> bodyModel = new BodyModel<>(request, exception.getStatus(), payload, exception.getMessage());
		log(bodyModel, exception);
		return bodyModel;
	}
	
	@Nonnull
	private BodyModelResponse<?> buildBodyModelResponse(@Nonnull StatusException exception,
			@Nonnull HttpServletRequest request) {
		return new BodyModelResponse<>(buildBodyModel(exception, request));
	}
	
	@Nonnull
	private ModelAndView buildModelAndView(@Nonnull StatusException exception, @Nonnull HttpServletRequest request) {
		BodyModel<?> bodyModel = buildBodyModel(exception, request);
		ModelAndView modelAndView = new ModelAndView();
		View errorView = viewProvider.getView(getErrorViewName(String.valueOf(bodyModel.getStatus())));
		if (errorView == null) {
			modelAndView.setViewName(getErrorViewName(defaultViewNameSuffix));
		} else {
			modelAndView.setView(errorView);
		}
		modelAndView.setStatus(bodyModel.getStatus());
		webMvcControllerAdvice.addAttributes(modelAndView.getModel()::put, request);
		modelAndView.getModel().put(WebConstants.MA_N_ERROR, bodyModel);
		return modelAndView;
	}
	
	@ExceptionHandler
	public Object handleException(StatusException exception, HttpServletRequest request) {
		return isApiRequest(request) ? buildBodyModelResponse(exception, request)
				: buildModelAndView(exception, request);
	}
	
	@ExceptionHandler
	public Object handleException(ModelNotFoundException exception, HttpServletRequest request) {
		return handleException(NotFoundException.ofAttributes(exception.getModelType(), exception.getAttributes()),
				request);
	}
	
	@ExceptionHandler
	public Object handleException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
		Map<String,String> parameters = Collections.singletonMap(exception.getName(), exception.getMessage());
		return handleException(new UnprocessableEntityException(exception, parameters), request);
	}
	
	@ExceptionHandler
	public Object handleException(ConstraintViolationException exception, HttpServletRequest request) {
		Map<String,String> parameters = exception.getConstraintViolations().stream()
				.collect(Collectors.toMap(cv -> cv.getPropertyPath().toString(), ConstraintViolation::getMessage));
		return handleException(new UnprocessableEntityException(exception, parameters), request);
	}
	
	@Nullable
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException exception,
			@Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
		Map<String,String> parameters = exception.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, error -> String.valueOf(error.getDefaultMessage())));
		return handleExceptionInternal(exception, parameters, headers, status, request);
	}
	
	@Nullable
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(@Nonnull Exception exception, @Nullable Object body,
			@Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
		String details = null;
		if (exception instanceof ErrorResponse errorResponse) {
			details = errorResponse.getBody().getDetail();
		}
		BodyModel<?> bodyModel = new BodyModel<>(request, status, body, details);
		log(bodyModel, exception);
		if (WebUtils.isResponseCommitted(request)) {
			logger.warn(Msg.format("Response already committed. Ignoring exception: {}", exception));
			return null;
		}
		return new ResponseEntity<>(bodyModel, headers, status);
	}
	
	@Nonnull
	protected ResponseEntity<Object> createResponseEntity(@Nullable Object body, @Nonnull HttpHeaders headers,
			@Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
		return new ResponseEntity<>(new BodyModel<>(request, status, body), headers, status);
	}
	
}
