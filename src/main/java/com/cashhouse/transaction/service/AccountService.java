package com.cashhouse.transaction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cashhouse.transaction.model.Account;

public interface AccountService {

	public Account findById(long id);

	public Page<Account> findAll(Pageable pageable);

	public Account create(Account account);

	public Account update(long id, Account account);

	public void delete(long id);

}
