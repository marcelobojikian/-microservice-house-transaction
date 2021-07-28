package com.cashhouse.transaction.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	private static final String ACCOUNT_NOT_FOUND = "account.not.found";

	private AccountRepository accountRepository;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account findById(long id) {
		return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND));
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	@Override
	public Account create(Account account) {

		log.info(String.format("Creating new Account"));

		return accountRepository.save(account);
	}

	@Override
	public Account update(long id, Account account) {

		Account entity = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND));

		log.info(String.format("Account %s changing ... ", entity.getId()));
		log.info(String.format("Name[%s], Started[%s], Balance[%s]", account.getId(), account.getStarted(),
				account.getBalance()));

		entity.setStarted(account.getStarted());
		entity.setBalance(account.getBalance());

		return accountRepository.save(entity);
	}

	@Override
	@Transactional
	public void delete(long id) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND));

		log.info(String.format("Deleting Account %s", account.getId()));

		accountRepository.delete(account);

		log.info("Delete sucess");

	}

}
