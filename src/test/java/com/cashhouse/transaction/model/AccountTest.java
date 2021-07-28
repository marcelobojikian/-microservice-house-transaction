package com.cashhouse.transaction.model;

import static com.cashhouse.transaction.util.BuilderFactory.createAccount;
import static com.cashhouse.transaction.util.BuilderFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.cashhouse.transaction.model.Transaction.Action;

class AccountTest {

	@Test
	void whenCreateAccount_thenReturnValidEntityObject() {
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();
		
		account.setTransactions(Arrays.asList(transaction));

		assertEquals(new BigDecimal("2.00"), account.getStarted());
		assertEquals(new BigDecimal("10.00"), account.getBalance());
		assertTrue(account.getTransactions().size() == 1);

	}

	@Test
	void whenDoDepoist_thenReturnValidEntityObject() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();

		account.doDeposit(new BigDecimal("5.50"));
		
		assertEquals(new BigDecimal("15.50"), account.getBalance());
		
	}

	@Test
	void whenDoWithdraw_thenReturnValidEntityObject() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();

		account.doWithdraw(new BigDecimal("5.50"));
		
		assertEquals(new BigDecimal("4.50"), account.getBalance());
		
	}

}
