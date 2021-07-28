package com.cashhouse.transaction.model;

import static com.cashhouse.transaction.util.BuilderFactory.createAccount;
import static com.cashhouse.transaction.util.BuilderFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.model.Transaction.Status;

class TransactionTest {

	@Test
	void whenCreateTransaction_thenReturnValidEntityObject() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.DEPOSIT)
									.value("3.33").result();
		
		transaction.setAccount(account);
		
		assertEquals(Status.SENDED, transaction.getStatus());
		assertEquals(Action.DEPOSIT, transaction.getAction());
		assertEquals(new BigDecimal("3.33"), transaction.getValue());
		assertEquals(account, transaction.getAccount());
		
	}

	@Test
	void whenCommitDeposit_thenReturnValidEntityObject() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.DEPOSIT)
									.value("3.33").result();

		transaction.setAccount(account);
		transaction.commit();
		
		assertEquals(new BigDecimal("13.33"), account.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithdraw_thenReturnValidEntityObject() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();

		transaction.setAccount(account);
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), account.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithStatusFinished_thenThrowIllegalStateException() {
		
		Account account = createAccount()
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();
		
		transaction.setAccount(account);
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), account.getBalance());
		assertTrue(transaction.isFinished());

		assertThrows(IllegalStateException.class, () -> {
			transaction.commit();
		});
		
		
	}

}
