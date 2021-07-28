package com.cashhouse.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.cashhouse.transaction.model.Transaction.Action;

public class TransactionBuilder {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Transaction transaction;
	
	private TransactionBuilder() {
		transaction = new Transaction(); 
	}
	
	public static TransactionBuilder createBuilder() {
		return new TransactionBuilder();
	}
	
	public TransactionBuilder id(Long id) {
		transaction.setId(id);
		return this;
	}
	
	public TransactionBuilder value(String value) {
		return value(new BigDecimal(value));
	}
	
	public TransactionBuilder value(BigDecimal value) {
		transaction.setValue(value);
		return this;
	}
	
	public TransactionBuilder action(Action action) {
		transaction.setAction(action);
		return this;
	}
	
	public TransactionBuilder account(Account account) {
		transaction.setAccount(account);
		return this;
	}
	
	public TransactionBuilder createdDateNow() {
		return createdDate(LocalDateTime.now());
	}
	
	public TransactionBuilder createdDate(String date) {
		LocalDateTime createdDale = LocalDateTime.parse(date, formatter);
		return createdDate(createdDale);
	}
	
	public TransactionBuilder createdDate(LocalDateTime createdDate) {
		transaction.setCreatedDate(createdDate);
		return this;
	}
	
	public Transaction result() {
		return transaction;
	}

}
