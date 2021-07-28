package com.cashhouse.transaction.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Locale;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cashhouse.transaction.dto.factory.PageableDto;
import com.cashhouse.transaction.dto.request.transaction.CreateTransaction;
import com.cashhouse.transaction.dto.response.TransactionListDtoFactory;
import com.cashhouse.transaction.dto.response.transaction.TransactionDateHeaderDto;
import com.cashhouse.transaction.dto.response.transaction.TransactionDetailDto;
import com.cashhouse.transaction.model.Transaction;
import com.cashhouse.transaction.service.TransactionService;
import com.querydsl.core.types.Predicate;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionListDtoFactory factory;

	@GetMapping("")
	@ApiOperation(value = "Return a list with all transaction", response = TransactionDetailDto[].class)
	public ResponseEntity findAll(
			@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) String language,
			@QuerydslPredicate(root = Transaction.class) Predicate predicate,
			@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Direction.DESC) Pageable pageable) {

		Page<Transaction> transactions = transactionService.findAll(predicate, pageable);

		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		PageableDto<Transaction> dto = factory.getListDto(pageable);

		if (language != null && dto instanceof TransactionDateHeaderDto) {
			dto = new TransactionDateHeaderDto(Locale.forLanguageTag(language));
		}

		boolean isPartialPage = transactions.getNumberOfElements() < transactions.getTotalElements();
		HttpStatus httpStatus = isPartialPage ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;

		return new ResponseEntity<>(dto.asPage(transactions, pageable), httpStatus);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return transaction entity by id", response = TransactionDetailDto.class)
	public TransactionDetailDto findById(@PathVariable Long id) {
		Transaction transaction = transactionService.findById(id);
		return new TransactionDetailDto(transaction);
	}

	@PostMapping("/deposit")
	@ApiOperation(value = "Returns transaction deposit created entity", response = TransactionDetailDto.class)
	public ResponseEntity<TransactionDetailDto> createDepoist(@RequestBody @Valid CreateTransaction content,
			UriComponentsBuilder uriBuilder) {

		Long accountId = content.getAccount();
		BigDecimal value = content.getValue();

		Transaction entity = transactionService.createDeposit(accountId, value);

		URI uri = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).body(new TransactionDetailDto(entity));

	}

	@PostMapping("/withdraw")
	@ApiOperation(value = "Returns transaction withdraw created entity", response = TransactionDetailDto.class)
	public ResponseEntity<TransactionDetailDto> createWithdraw(@RequestBody @Valid CreateTransaction content,
			UriComponentsBuilder uriBuilder) {

		Long accountId = content.getAccount();
		BigDecimal value = content.getValue();

		Transaction entity = transactionService.createWithdraw(accountId, value);

		URI uri = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).body(new TransactionDetailDto(entity));

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted", response = TransactionDetailDto.class)
	public void detele(@PathVariable Long id) {
		transactionService.delete(id);
	}

}
