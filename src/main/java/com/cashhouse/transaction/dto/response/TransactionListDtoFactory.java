package com.cashhouse.transaction.dto.response;

import com.cashhouse.transaction.dto.factory.ResponseListDtoFactory;
import com.cashhouse.transaction.dto.factory.type.SimpleListDto;
import com.cashhouse.transaction.dto.response.transaction.TransactionDetailDto;
import com.cashhouse.transaction.model.Transaction;

public class TransactionListDtoFactory extends ResponseListDtoFactory<TransactionDetailDto, Transaction> {

	public TransactionListDtoFactory(SimpleListDto<TransactionDetailDto, Transaction> defaultList) {
		super(defaultList);
	}

}
