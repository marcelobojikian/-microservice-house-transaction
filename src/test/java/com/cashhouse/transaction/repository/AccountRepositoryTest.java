package com.cashhouse.transaction.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cashhouse.transaction.model.Account;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	@Test
	public void mustReturnAccountNull() {
		Optional<Account> entity = repository.findById(-1);
		assertTrue(entity.isEmpty());
	}

	@Test
	public void mustReturnAccountCodeOne() {
		Optional<Account> entity = repository.findById(1);
		assertTrue(entity.isPresent());
		
		Account account = entity.get();
		
		assertEquals(account.getTransactions().size(), 2);
		
	}

	@Test
	public void mustReturnAccountCodeTwo() {
		Optional<Account> entity = repository.findById(2);
		assertTrue(entity.isPresent());
		
		Account account = entity.get();
		
		assertEquals(account.getTransactions().size(), 1);
	}

	@Test
	public void mustReturnAccountCodeThree() {
		Optional<Account> entity = repository.findById(3);
		assertTrue(entity.isPresent());
	}

}
