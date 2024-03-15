package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.View;

import java.util.Locale;

public interface ViewProvider {
	
	@Nullable
	View getView(@Nonnull String viewName, @Nonnull Locale locale);
	
	@Nullable
	View getView(@Nonnull String viewName);
	
}
