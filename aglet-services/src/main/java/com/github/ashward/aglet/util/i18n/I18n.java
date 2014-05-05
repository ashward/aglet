package com.github.ashward.aglet.util.i18n;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class I18n implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	private final Locale locale;

	I18n(final Locale locale) {
		this.locale = locale;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public String msg(final String id, final String... args) {
		return applicationContext.getMessage(id, args, locale);
	}
}
