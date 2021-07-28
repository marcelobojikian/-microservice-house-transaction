package com.cashhouse.transaction.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.model.Transaction;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public Docket transactionApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cashhouse.transaction"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Account.class, Transaction.class);
	}

}
