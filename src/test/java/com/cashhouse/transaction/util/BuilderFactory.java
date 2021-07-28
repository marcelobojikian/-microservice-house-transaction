package com.cashhouse.transaction.util;

import com.cashhouse.transaction.model.AccountBuilder;
import com.cashhouse.transaction.model.TransactionBuilder;

public class BuilderFactory {
	
	private BuilderFactory() {
		
	}
	
	public static AccountBuilder createAccount() {
		return AccountBuilder.createBuilder();
	}
	
	public static TransactionBuilder createTransaction() {
		return TransactionBuilder.createBuilder();
	}

}
