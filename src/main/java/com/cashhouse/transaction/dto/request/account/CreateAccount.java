package com.cashhouse.transaction.dto.request.account;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.cashhouse.transaction.model.Account;

import lombok.Setter;

@Setter
public class CreateAccount {

	@NumberFormat(style = Style.CURRENCY)
	@Digits(integer = 10, fraction = 2)
	BigDecimal started;

	@NotNull
	@NumberFormat(style = Style.CURRENCY)
	@Digits(integer = 10, fraction = 2)
	BigDecimal balance;

	public Account toEntity() {
		if (started == null) {
			started = balance;
		}
		return new Account(started, balance);
	}

}
