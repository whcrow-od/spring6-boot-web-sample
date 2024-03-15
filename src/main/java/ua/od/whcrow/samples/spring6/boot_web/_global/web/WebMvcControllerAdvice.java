package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.ZonedDateTime;
import java.util.function.BiConsumer;

@ControllerAdvice
class WebMvcControllerAdvice {
	
	@Value("${spring.application.name}")
	private String appName;
	
	@InitBinder
	public void initBinder(@Nonnull WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	public void addAttributes(@Nonnull BiConsumer<String,Object> attributeAdder, @Nonnull HttpServletRequest request) {
		attributeAdder.accept(WebConstants.MA_N_APP_NAME, appName);
		attributeAdder.accept(WebConstants.MA_N_SERVER_DATE_TIME, ZonedDateTime.now());
		attributeAdder.accept(WebConstants.MA_N_REQUEST_URL_PATH, request.getRequestURI());
		HttpSession httpSession = request.getSession(false);
		attributeAdder.accept(WebConstants.MA_N_USER,
				httpSession == null ? null : httpSession.getAttribute(WebConstants.SA_N_USER));
	}
	
	// Adds the following attributes to all models defined in all controller class
	@ModelAttribute
	public void addAttributes(@Nonnull Model model, @Nonnull HttpServletRequest request) {
		addAttributes(model::addAttribute, request);
	}
	
}
