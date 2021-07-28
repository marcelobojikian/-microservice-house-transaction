package com.cashhouse.transaction.repository;

import static com.cashhouse.transaction.util.BuilderFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.model.Transaction;
import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.model.Transaction.Status;

@DataJpaTest
public class TransactionRepositoryTest {
	
	@Autowired
	private TestEntityManager em;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void mustReturnTransactionCreated() {
		
		Optional<Account> optAccount = accountRepository.findById(3);
		assertTrue(optAccount.isPresent());
		
		Account account = optAccount.get();

		Transaction transaction = createTransaction()
									.action(Action.DEPOSIT)
									.value("99.98").result();

		transaction.setAccount(account);

		Long idCreated = em.persist(transaction).getId();
		
		Optional<Transaction> optTransaction = transactionRepository.findById(idCreated);
		assertTrue(optTransaction.isPresent());
		
		Transaction transactionCreated = optTransaction.get();

		assertNotNull(transactionCreated);
		assertNotNull(transactionCreated.getCreatedDate());
		assertNull(transactionCreated.getUpdatedDate());
		assertEquals(account, transactionCreated.getAccount());
		assertEquals(Action.DEPOSIT, transactionCreated.getAction());
		assertEquals(Status.SENDED, transactionCreated.getStatus());
		assertTrue(new BigDecimal("99.98").equals(transactionCreated.getValue()));
		
		transactionCreated.setValue(new BigDecimal("1.22"));
		
		em.persistAndFlush(transactionCreated);

		Optional<Transaction> optTransactionUpdate = transactionRepository.findById(idCreated);
		assertTrue(optTransactionUpdate.isPresent());
		Transaction transactionUpdate = optTransactionUpdate.get();

		assertNotNull(transactionUpdate.getUpdatedDate());

	}

	@Test
	public void mustReturnOneTransaction() {

		Optional<Account> optAccount = accountRepository.findById(2);
		assertTrue(optAccount.isPresent());
		
		Account account = optAccount.get();

		List<Transaction> transactions = transactionRepository.findByAccount(account);
		
		assertTrue(transactions.size() == 1);
		
	}

	@Test
	public void mustReturnTransactionNull() {
		Optional<Transaction> transaction = transactionRepository.findById(-1);
		assertTrue(transaction.isEmpty());
	}

}
