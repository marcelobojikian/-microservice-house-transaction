package com.cashhouse.transaction.service;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.model.Transaction;
import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.repository.AccountRepository;
import com.cashhouse.transaction.repository.TransactionRepository;
import com.querydsl.core.types.Predicate;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final String ACCOUNT_NOT_FOUND = "account.not.found";
	private static final String TRANSACTION_NOT_FOUND = "transaction.not.found";

	private AccountRepository accountRepository;

	private TransactionRepository transactionRepository;

	@Autowired
	public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction findById(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));
	}

	@Override
	public Page<Transaction> findAll(Predicate predicate, Pageable pageable) {
		return transactionRepository.findAll(predicate, pageable);
	}

	@Override
	@Transactional
	public Transaction createDeposit(Long accountId, BigDecimal value) {
		return create(accountId, value, Action.DEPOSIT);
	}

	@Override
	@Transactional
	public Transaction createWithdraw(Long accountId, BigDecimal value) {
		return create(accountId, value, Action.WITHDRAW);
	}

	private Transaction create(Long accountId, BigDecimal value, Action action) {

		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND));

		Transaction transaction = new Transaction();

		transaction.setAction(action);
		transaction.setAccount(account);
		transaction.setValue(value);

		transaction = transactionRepository.save(transaction);

		return transaction;

	}

	@Override
	public void delete(Long id) {

		Transaction entity = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));

		transactionRepository.delete(entity);

	}

	@Override
	@Transactional
	public synchronized Transaction finish(Long id) {

		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));

		transaction.commit();

		return transactionRepository.save(transaction);

	}

}
