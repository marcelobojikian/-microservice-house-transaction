package com.cashhouse.transaction.dto.response.account;

import com.cashhouse.transaction.dto.factory.type.SimpleListDto;
import com.cashhouse.transaction.model.Account;

public class AccountListDto extends SimpleListDto<AccountDetailDto, Account> {

	@Override
	public AccountDetailDto getContent(Account accounts) {
		return new AccountDetailDto(accounts);
	}

}
