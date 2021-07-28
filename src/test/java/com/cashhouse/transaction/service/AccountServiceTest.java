package com.cashhouse.transaction.service;

import static com.cashhouse.transaction.util.BuilderFactory.createAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.repository.AccountRepository;

class AccountServiceTest {

	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	@Test
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(accountRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			accountService.findById(1L);
		});

	}

	@Test
	public void whenFindById_thenReturnEntityObject() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();

		when(accountRepository.findById(1l)).thenReturn(Optional.of(energy));

		Account accountExpect = accountService.findById(1L);
		
		verify(accountRepository).findById(1l);
		assertEquals(accountExpect, energy);

	}

	@Test
	public void whenFindAll_thenReturnObjectArray() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();
		
		Account garbage = createAccount()
				.id(2l)
				.started("4.2")
				.balance("56.6").result();

		PageRequest pagination = PageRequest.of(1, 10);

		List<Account> accounts = Arrays.asList(energy, garbage);
		Page<Account> accountsPage = new PageImpl<>(accounts, pagination, accounts.size());

		when(accountRepository.findAll(pagination)).thenReturn(accountsPage);

		Page<Account> results = accountService.findAll(pagination);

		assertEquals(results, accountsPage);

	}

	@Test
	public void whenCreate_thenReturnEntityObject() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();

		when(accountRepository.save(energy)).thenReturn(energy);

		Account account = accountService.create(energy);

		verify(accountRepository).save(energy);
		assertEquals(account.getId(), 1l);
		assertEquals(account.getStarted(), BigDecimal.valueOf(12.3));
		assertEquals(account.getBalance(), BigDecimal.valueOf(12.3));

	}

	@Test
	public void whenUpdate_thenReturnEntityObject() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();
		
		Account energyNew = createAccount()
				.id(1l)
				.started("3.1")
				.balance("3.2").result();

		when(accountRepository.findById(1l)).thenReturn(Optional.of(energy));
		when(accountRepository.save(energy)).thenReturn(energy);

		Account account = accountService.update(1l, energyNew);

		verify(accountRepository).save(energy);
		assertEquals(account.getId(), 1l);
		assertEquals(account.getStarted(), BigDecimal.valueOf(3.1));
		assertEquals(account.getBalance(), BigDecimal.valueOf(3.2));

	}

	@Test
	public void whenUpdate_thenThrowEntityNotFoundException() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();

		when(accountRepository.findById(99l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			accountService.update(99l, energy);
		});

	}

	@Test
	public void whenDelete_thenReturnVoid() throws Exception {
		
		Account energy = createAccount()
				.id(1l)
				.started("12.3").result();

		when(accountRepository.findById(1l)).thenReturn(Optional.of(energy));
		doNothing().when(accountRepository).delete(energy);

		accountService.delete(1l);
		
		verify(accountRepository).delete(energy);

	}

	@Test
	public void whenDelete_thenThrowEntityNotFoundException() throws Exception {

		when(accountRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			accountService.delete(1l);
		});

	}

}
