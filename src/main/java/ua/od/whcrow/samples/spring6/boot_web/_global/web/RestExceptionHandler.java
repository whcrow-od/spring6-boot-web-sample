package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.ParameterException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.StatusException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.UnprocessableEntityException;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	// MC - message code
	private static final String MC_PROBLEM_DETAIL_404 = "app.web.problemDetail.404";
	private static final String MC_PROBLEM_DETAIL_500 = "app.web.problemDetail.500";
	
	@Nullable
	private ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
		HttpStatusCode statusCode;
		HttpHeaders headers;
		ProblemDetail body;
		if (exception instanceof ErrorResponse errorResponse) {
			statusCode = errorResponse.getStatusCode();
			body = ProblemDetail.forStatus(statusCode);
			headers = errorResponse.getHeaders();
		} else {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			body = createProblemDetail(exception, statusCode, statusCode.toString(), MC_PROBLEM_DETAIL_500,
					null, request);
			headers = HttpHeaders.EMPTY;
		}
		return handleExceptionInternal(exception, body, headers, statusCode, request);
	}
	
	@Nullable
	@ExceptionHandler
	public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest request) {
		switch (exception) {
			case ModelNotFoundException modelNotFoundException -> {
				return handleException(modelNotFoundException, request);
			}
			case StatusException statusException -> {
				return handleException(statusException, request);
			}
			case MethodArgumentTypeMismatchException methodArgumentTypeMismatchException -> {
				return handleException(methodArgumentTypeMismatchException, request);
			}
			case ConstraintViolationException constraintViolationException -> {
				return handleException(constraintViolationException, request);
			}
			default -> {
				try {
					return handleException(exception, request);
				} catch (Exception e) {
					return handleUnknownException(e, request);
				}
			}
		}
	}
	
	@Nullable
	@ExceptionHandler
	public ResponseEntity<Object> handleException(ModelNotFoundException exception, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemDetail body = createProblemDetail(exception, status, status.toString(), MC_PROBLEM_DETAIL_404,
				new Object[]{exception.getModelType().getSimpleName()}, request);
		body.setProperties(exception.getAttributes());
		return handleExceptionInternal(exception, body, HttpHeaders.EMPTY, status, request);
	}
	
	@Nullable
	@ExceptionHandler
	public ResponseEntity<Object> handleException(StatusException exception, WebRequest request) {
		ProblemDetail body = ProblemDetail.forStatusAndDetail(exception.getStatus(), exception.getMessage());
		if (exception instanceof ParameterException parameterException) {
			body.setProperties(parameterException.getParameters());
		} else if (exception instanceof UnprocessableEntityException unprocessableEntityException) {
			body.setProperties(unprocessableEntityException.getParameters());
		}
		return handleExceptionInternal(exception, body, exception.getHeaders(), exception.getStatus(), request);
	}
	
	@Nullable
	@ExceptionHandler
	public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException exception, WebRequest request) {
		ProblemDetail body = createProblemDetail(exception, HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(),
				null, null, request);
		body.setProperty(exception.getName(), exception.getValue());
		return handleExceptionInternal(exception, body, HttpHeaders.EMPTY, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	@Nullable
	@ExceptionHandler
	public ResponseEntity<Object> handleException(ConstraintViolationException exception, WebRequest request) {
		ProblemDetail body = createProblemDetail(exception, HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(),
				null, null, request);
		Map<String,Object> parameters = exception.getConstraintViolations().stream()
				.collect(Collectors.toMap(cv -> cv.getPropertyPath().toString(), ConstraintViolation::getMessage));
		body.setProperties(parameters);
		return handleExceptionInternal(exception, body, HttpHeaders.EMPTY, HttpStatus.UNPROCESSABLE_ENTITY, request);
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
		ResponseEntity<Object> entity = super.handleExceptionInternal(exception, body, headers, status, request);
		if (entity == null) {
			// This happens only if response already committed.
			return null;
		}
		if (entity.getBody() == null) {
			entity = new ResponseEntity<>(ProblemDetail.forStatus(entity.getStatusCode()), entity.getHeaders(),
					entity.getStatusCode());
		}
		if (entity.getBody() instanceof ProblemDetail problemDetail && problemDetail.getInstance() == null) {
			problemDetail.setInstance(URI.create(WebUtils.getHttpServletRequest(request).getRequestURI()));
		}
		return entity;
	}
	
}
