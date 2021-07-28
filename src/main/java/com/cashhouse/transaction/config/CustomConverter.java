package com.cashhouse.transaction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cashhouse.transaction.converter.ActionToEnumConverter;
import com.cashhouse.transaction.converter.StatusToEnumConverter;

@Configuration
public class CustomConverter implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ActionToEnumConverter());
		registry.addConverter(new StatusToEnumConverter());
	}

}
