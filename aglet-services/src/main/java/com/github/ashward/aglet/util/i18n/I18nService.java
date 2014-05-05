package com.github.ashward.aglet.util.i18n;

import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class I18nService {
	public I18n getForLocale(final Locale locale) {
		return new I18n(locale);
	}
}
