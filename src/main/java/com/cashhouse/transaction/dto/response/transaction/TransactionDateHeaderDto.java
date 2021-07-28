package com.cashhouse.transaction.dto.response.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import com.cashhouse.transaction.dto.factory.type.GroupListDto;
import com.cashhouse.transaction.model.Transaction;

public class TransactionDateHeaderDto extends GroupListDto<TransactionDetailDto, Transaction> {

	private DateTimeFormatter formatter;

	public TransactionDateHeaderDto() {
		this(FormatStyle.LONG, LocaleContextHolder.getLocale());
	}

	public TransactionDateHeaderDto(Locale locale) {
		this(FormatStyle.LONG, locale);
	}

	public TransactionDateHeaderDto(FormatStyle formatStyle, Locale locale) {
		this.formatter = DateTimeFormatter.ofLocalizedDate(formatStyle).withLocale(locale);
	}

	@Override
	public String getHeader(Transaction transaction) {
		LocalDateTime createdDate = transaction.getCreatedDate();
		return createdDate.format(formatter);
	}

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
