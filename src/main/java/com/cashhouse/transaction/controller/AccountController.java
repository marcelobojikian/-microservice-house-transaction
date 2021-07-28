package com.cashhouse.transaction.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cashhouse.transaction.dto.factory.PageableDto;
import com.cashhouse.transaction.dto.request.account.CreateAccount;
import com.cashhouse.transaction.dto.request.account.EntityAccount;
import com.cashhouse.transaction.dto.response.AccountListDtoFactory;
import com.cashhouse.transaction.dto.response.account.AccountDetailDto;
import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.service.AccountService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountListDtoFactory factory;

	@GetMapping("")
	@ApiOperation(value = "Return list with all accounts", response = AccountDetailDto[].class)
	public ResponseEntity findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {

		Page<Account> account = accountService.findAll(pageable);

		if (account.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		PageableDto<Account> dto = factory.getListDto(pageable);

		boolean isPartialPage = account.getNumberOfElements() < account.getTotalElements();
		HttpStatus httpStatus = isPartialPage ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;

		return new ResponseEntity<>(dto.asPage(account, pageable), httpStatus);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return account entity by id", response = AccountDetailDto.class)
	public AccountDetailDto findById(@PathVariable Long id) {
		Account account = accountService.findById(id);
		return new AccountDetailDto(account);
	}

	@PostMapping("")
	@ApiOperation(value = "Return account entity created", response = AccountDetailDto.class)
	public ResponseEntity<AccountDetailDto> create(@RequestBody @Valid CreateAccount dto,
			UriComponentsBuilder uriBuilder) {

		Account account = dto.toEntity();

		Account entity = accountService.create(account);

		URI uri = uriBuilder.path("/api/v1/accounts/{id}").buildAndExpand(entity.getId()).toUri();
		return ResponseEntity.created(uri).body(new AccountDetailDto(entity));

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Return account entity updated", response = AccountDetailDto.class)
	public AccountDetailDto update(@PathVariable Long id, @RequestBody @Valid EntityAccount dto) {
		Account entity = accountService.update(id, dto.toEntity());
		return new AccountDetailDto(entity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted")
	public void detele(@PathVariable Long id) {
		accountService.delete(id);
	}

}
