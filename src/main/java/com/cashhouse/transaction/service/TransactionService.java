package com.cashhouse.transaction.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cashhouse.transaction.model.Transaction;
import com.querydsl.core.types.Predicate;

public interface TransactionService {

	public Transaction findById(Long id);

	public Page<Transaction> findAll(Predicate predicate, Pageable pageable);

	public Transaction createDeposit(Long accountId, BigDecimal value);

	public Transaction createWithdraw(Long accountId, BigDecimal value);

	public void delete(Long id);

	/* Actions */

	public Transaction finish(Long id);
	

}
