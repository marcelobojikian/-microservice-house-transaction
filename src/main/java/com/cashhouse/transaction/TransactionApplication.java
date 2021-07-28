package com.cashhouse.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.cashhouse.transaction.dto.response.AccountListDtoFactory;
import com.cashhouse.transaction.dto.response.TransactionListDtoFactory;
import com.cashhouse.transaction.dto.response.account.AccountListDto;
import com.cashhouse.transaction.dto.response.transaction.TransactionDateHeaderDto;
import com.cashhouse.transaction.dto.response.transaction.TransactionListDto;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}

	@Bean
	public TransactionListDtoFactory getTransactionListDtoFactory() {
		TransactionListDtoFactory factory = new TransactionListDtoFactory(new TransactionListDto());
		factory.addField("createdDate", new TransactionDateHeaderDto());
		return factory;
	}

	@Bean
	public AccountListDtoFactory getAccountListDtoFactory() {
		return new AccountListDtoFactory(new AccountListDto());
	}
	
}
