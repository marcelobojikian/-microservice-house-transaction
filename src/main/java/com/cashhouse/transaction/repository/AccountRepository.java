package com.cashhouse.transaction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cashhouse.transaction.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findById(@Param("id") long id);

}
