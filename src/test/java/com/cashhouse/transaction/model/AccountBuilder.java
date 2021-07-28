package com.cashhouse.transaction.model;

import java.math.BigDecimal;

public class AccountBuilder {
	
	private Account account;

	private AccountBuilder() {
		account = new Account();
	}
	
	public static AccountBuilder createBuilder() {
		return new AccountBuilder();
	}
	
	public AccountBuilder id(Long id) {
		account.setId(id);;
		return this;
	}
	
	public AccountBuilder started(String started) {
		return started(new BigDecimal(started));
	}
	
	public AccountBuilder started(BigDecimal started) {
		account.setStarted(started);
		if(account.getBalance() == null) {
			account.setBalance(started);
		}
		return this;
	}
	
	public AccountBuilder balance(String balance) {
		return balance(new BigDecimal(balance));
	}
	
	public AccountBuilder balance(BigDecimal balance) {
		account.setBalance(balance);
		return this;
	}
	
	public Account result() {
		return account;
	}

}
