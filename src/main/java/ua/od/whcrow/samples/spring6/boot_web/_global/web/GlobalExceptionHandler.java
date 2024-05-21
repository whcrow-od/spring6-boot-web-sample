package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.ViewProvider;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {
	
	private final String viewNamePrefix;
	private final String defaultViewName;
	private final RestExceptionHandler defaultExceptionHandler;
	private final ViewProvider viewProvider;
	private final WebMvcControllerAdvice webMvcControllerAdvice;
	
	public GlobalExceptionHandler(@Value(WebConstants.AP_VP_ERROR_VIEW_NAME_PREFIX) String viewNamePrefix,
			@Value(WebConstants.AP_VP_ERROR_DEFAULT_VIEW_NAME_SUFFIX) String defaultViewNameSuffix,
			RestExceptionHandler defaultExceptionHandler, ViewProvider viewProvider,
			WebMvcControllerAdvice webMvcControllerAdvice) {
		this.viewNamePrefix = viewNamePrefix;
		this.defaultViewName = getErrorViewName(defaultViewNameSuffix);
		this.defaultExceptionHandler = defaultExceptionHandler;
		this.viewProvider = viewProvider;
		this.webMvcControllerAdvice = webMvcControllerAdvice;
	}
	
	@Nonnull
	private String getErrorViewName(@Nonnull String viewNameSuffix) {
		return viewNamePrefix + viewNameSuffix;
	}
	
	@Nonnull
	public String getErrorViewName(@Nonnull HttpStatusCode statusCode) {
		Assert.notNull(statusCode, "statusCode");
		return getErrorViewName(String.valueOf(statusCode.value()));
	}
	
	@Nullable
	@ExceptionHandler
	public Object handleException(Exception exception, WebRequest request) {
		ResponseEntity<Object> responseEntity = defaultExceptionHandler.handleAnyException(exception, request);
		if (responseEntity == null) {
			// This happens only if response already committed.
			return null;
		}
		if (responseEntity.getStatusCode().is5xxServerError()) {
			log.error(Msg.format("Issue: {}", responseEntity.getBody()), exception);
		} else {
			log.warn(Msg.format("Issue: {}; caused by {}", responseEntity.getBody(), exception));
		}
		if (WebUtils.isApiRequest(request)) {
			return responseEntity;
		}
		ModelAndView modelAndView = new ModelAndView();
		View errorView = viewProvider.getView(getErrorViewName(responseEntity.getStatusCode()));
		if (errorView == null) {
			modelAndView.setViewName(defaultViewName);
		} else {
			modelAndView.setView(errorView);
		}
		modelAndView.setStatus(responseEntity.getStatusCode());
		webMvcControllerAdvice.addAttributes(modelAndView.getModel()::put, request);
		modelAndView.getModel().put(WebConstants.MA_N_ERROR, responseEntity.getBody());
		return modelAndView;
	}
	
}
