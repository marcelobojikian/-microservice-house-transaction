package com.cashhouse.transaction.converter;

import org.springframework.core.convert.converter.Converter;

import com.cashhouse.transaction.model.Transaction.Status;

public class StatusToEnumConverter implements Converter<String, Status> {

	@Override
	public Status convert(String source) {
		return Status.valueOf(source.toUpperCase());
	}

}
