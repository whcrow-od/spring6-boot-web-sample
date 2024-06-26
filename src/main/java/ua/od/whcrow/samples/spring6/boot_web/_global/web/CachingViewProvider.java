package ua.od.whcrow.samples.spring6.boot_web._global.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafView;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.ViewProvider;

import java.util.Locale;
import java.util.Objects;

// TODO: Add caching
@Slf4j
@Component
class CachingViewProvider implements ViewProvider {
	
	@Nullable
	private final Locale defaultLocale;
	private final ContentNegotiatingViewResolver viewResolver;
	private final SpringTemplateEngine thymeleafEngine;
	
	public CachingViewProvider(@Value(WebConstants.AP_VP_DEFAULT_LOCALE) String defaultLocaleStr,
			ContentNegotiatingViewResolver viewResolver, SpringTemplateEngine thymeleafEngine) {
		this.defaultLocale = StringUtils.isEmpty(defaultLocaleStr) ? null : LocaleUtils.toLocale(defaultLocaleStr);
		this.viewResolver = viewResolver;
		this.thymeleafEngine = thymeleafEngine;
	}
	
	private boolean isThymeleafViewResolvable(@Nonnull ThymeleafView view) {
		return thymeleafEngine.getConfiguration().getTemplateResolvers().stream()
				.map(resolver -> resolver.resolveTemplate(thymeleafEngine.getConfiguration(), null,
						view.getTemplateName(), null))
				.anyMatch(Objects::nonNull);
	}
	
	@Nullable
	@Override
	public View getView(@Nonnull String viewName, @Nonnull Locale locale) {
		Assert.notNull(viewName, "viewName");
		Assert.notNull(locale, "locale");
		try {
			View view = viewResolver.resolveViewName(viewName, locale);
			if (view == null) {
				return null;
			}
			// ThymeleafViewResolver does not actually check template for availability,
			// this check is performed only during template processing. So we need to check it ourselves.
			if (view instanceof ThymeleafView thymeleafView && !isThymeleafViewResolvable(thymeleafView)) {
				return null;
			}
			return view;
		} catch (Exception e) {
			log.error(Msg.format("Failed to provide a view '{}' for locale {}", viewName, locale), e);
			return null;
		}
	}
	
	@Nullable
	@Override
	public View getView(@Nonnull String viewName) {
		Locale contextualLocale = LocaleContextHolder.getLocale();
		View view = getView(viewName, contextualLocale);
		if (view != null) {
			return view;
		}
		return defaultLocale == null || defaultLocale.equals(contextualLocale) ? null
				: getView(viewName, defaultLocale);
	}
	
}
