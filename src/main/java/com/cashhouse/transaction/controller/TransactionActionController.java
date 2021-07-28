package com.cashhouse.transaction.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cashhouse.transaction.dto.response.transaction.TransactionDetailDto;
import com.cashhouse.transaction.model.Transaction;
import com.cashhouse.transaction.service.TransactionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/transactions/{id}")
public class TransactionActionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/finish")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "Returns finished transaction entity", response = Transaction.class)
	public ResponseEntity<TransactionDetailDto> finish(@PathVariable Long id, UriComponentsBuilder uriBuilder) {

		Transaction transaction = transactionService.finish(id);

		URI uri = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(transaction.getId()).toUri();
		return ResponseEntity.created(uri).body(new TransactionDetailDto(transaction));

	}

}
