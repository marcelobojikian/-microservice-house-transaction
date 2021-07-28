package com.cashhouse.transaction.dto.response;

import com.cashhouse.transaction.dto.factory.ResponseListDtoFactory;
import com.cashhouse.transaction.dto.factory.type.SimpleListDto;
import com.cashhouse.transaction.dto.response.account.AccountDetailDto;
import com.cashhouse.transaction.model.Account;

public class AccountListDtoFactory extends ResponseListDtoFactory<AccountDetailDto, Account> {

	public AccountListDtoFactory(SimpleListDto<AccountDetailDto, Account> defaultList) {
		super(defaultList);
	}

}
