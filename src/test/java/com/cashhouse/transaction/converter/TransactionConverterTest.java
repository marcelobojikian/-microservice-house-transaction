package com.cashhouse.transaction.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import com.cashhouse.transaction.model.Transaction.Action;
import com.cashhouse.transaction.model.Transaction.Status;

@SpringBootTest
public class TransactionConverterTest {

	@Autowired
	ConversionService conversionService;

	@Test
	public void whenConvertStringToStatus_thenSuccess() {

		assertEquals(conversionService.convert("sended", Status.class), Status.SENDED);
		assertEquals(conversionService.convert("finished", Status.class), Status.FINISHED);

	}

	@Test
	public void whenConvertStringToAction_thenSuccess() {

		assertEquals(conversionService.convert("deposit", Action.class), Action.DEPOSIT);
		assertEquals(conversionService.convert("withdraw", Action.class), Action.WITHDRAW);

	}

}