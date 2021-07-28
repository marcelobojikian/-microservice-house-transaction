package com.cashhouse.transaction.dto.response.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.cashhouse.transaction.controller.AccountController;
import com.cashhouse.transaction.model.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountDetailDto extends RepresentationModel<AccountDetailDto> {

	Long id;

	BigDecimal started;

	BigDecimal balance;

	public AccountDetailDto(Account account) {
		this.id = account.getId();
		this.started = account.getStarted();
		this.balance = account.getBalance();

		Link selfLink = linkTo(methodOn(AccountController.class).findById(id)).withSelfRel();
		this.add(selfLink);
	}

}
