package com.cashhouse.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.model.Transaction.Status;

public class EntityFactory {

	private EntityFactory() {

	}

	/*
	 * Transaction Helper
	 */

	public static Transaction createTransaction(Long id, BigDecimal value, Status status, Action action, Account account, LocalDateTime createdDate) {

		Transaction transaction = createTransaction(id, value, status, action, account);
		transaction.setCreatedDate(createdDate);

		return transaction;
	}

	public static Transaction createTransaction(Long id, BigDecimal value, Status status, Action action, Account account) {

		Transaction transaction = createTransaction(id, value, status, action);
		transaction.setAccount(account);

		return transaction;
	}

	public static Transaction createTransaction(Long id, BigDecimal value, Status status, Action action) {

		Transaction transaction = createTransaction(value, status, action);
		transaction.setId(id);

		return transaction;
	}

	public static Transaction createTransaction(BigDecimal value, Status status, Action action) {

		Transaction transaction = new Transaction();
		transaction.setValue(value);
//		transaction.setStatus(status);
		transaction.setAction(action);

		return transaction;
	}

	/*
	 * Account Helper
	 */

	public static Account createAccount(BigDecimal started) {
		return createAccount(started, started);
	}

	public static Account createAccount(Long id, BigDecimal started) {

		Account account = createAccount(started, started);
		account.setId(id);

		return account;
	}

	public static Account createAccount(Long id, BigDecimal started, BigDecimal balance) {

		Account account = createAccount(started, balance);
		account.setId(id);

		return account;
	}

	public static Account createAccount(BigDecimal started, BigDecimal balance) {

		Account account = new Account();
		account.setStarted(started);
		account.setBalance(balance);

		return account;
	}

}
