package com.cashhouse.transaction.converter;

import org.springframework.core.convert.converter.Converter;

import com.cashhouse.transaction.model.Transaction.Action;

public class ActionToEnumConverter implements Converter<String, Action> {

	@Override
	public Action convert(String source) {
		return Action.valueOf(source.toUpperCase());
	}

}
