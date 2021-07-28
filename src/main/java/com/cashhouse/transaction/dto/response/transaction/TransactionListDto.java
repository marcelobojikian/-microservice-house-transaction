package com.cashhouse.transaction.dto.response.transaction;

import com.cashhouse.transaction.dto.factory.type.SimpleListDto;
import com.cashhouse.transaction.model.Transaction;

public class TransactionListDto extends SimpleListDto<TransactionDetailDto, Transaction> {

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
