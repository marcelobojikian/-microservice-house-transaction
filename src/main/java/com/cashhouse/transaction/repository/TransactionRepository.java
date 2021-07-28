package com.cashhouse.transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.model.Transaction;

public interface TransactionRepository
		extends JpaRepository<Transaction, Long>, QuerydslPredicateExecutor<Transaction> {

	public Optional<Transaction> findById(@Param("id") long id);

	public List<Transaction> findByAccount(@Param("account") Account account);

}
