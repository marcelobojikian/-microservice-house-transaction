package com.cashhouse.transaction.dto.response.transaction;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.cashhouse.transaction.controller.AccountController;
import com.cashhouse.transaction.controller.TransactionController;
import com.cashhouse.transaction.dto.response.account.AccountDetailDto;
import com.cashhouse.transaction.model.Transaction;
import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.model.Transaction.Status;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionDetailDto extends RepresentationModel<TransactionDetailDto> {

	Long id;

	Long account;

	AccountDetailDto accountDto;

	Status status;

	Action action;

	BigDecimal value;

	LocalDateTime createdDate;

	LocalDateTime updatedDate;

	public TransactionDetailDto(Transaction transaction) {

		this.id = transaction.getId();
		this.account = transaction.getAccount().getId();
		this.status = transaction.getStatus();
		this.action = transaction.getAction();
		this.value = transaction.getValue();
		this.createdDate = transaction.getCreatedDate();
		this.updatedDate = transaction.getUpdatedDate();

		Link selfLink = linkTo(methodOn(TransactionController.class).findById(id)).withSelfRel();
		this.add(selfLink);

		Link accountLink = linkTo(methodOn(AccountController.class).findById(transaction.getAccount().getId())).withRel("account");
		this.add(accountLink);

	}

}
