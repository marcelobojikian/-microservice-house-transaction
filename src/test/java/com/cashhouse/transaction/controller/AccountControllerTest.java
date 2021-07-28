package com.cashhouse.transaction.controller;

import static com.cashhouse.transaction.model.EntityFactory.createAccount;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import com.cashhouse.transaction.config.validation.CustomRestValidationHandler;
import com.cashhouse.transaction.model.Account;
import com.cashhouse.transaction.service.AccountService;
import com.cashhouse.transaction.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest extends SampleRequest {

	private static final EntityNotFoundException accountNotFound = new EntityNotFoundException("account.not.found");

	@MockBean
	private CustomRestValidationHandler restValidationHandler;

	@MockBean
	private AccountService accountService;
	
	/**
	 * methods findById
	 */

	@Test
	void whenFindById_thenReturnEntityObject() throws Exception {

		Account entity = createAccount(3l, new BigDecimal("12.45"), new BigDecimal("3.11"));
		when(accountService.findById(3l)).thenReturn(entity);

		// @formatter:off
		get("/accounts/3")
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
	        .andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(3)))
			.andExpect(jsonPath("$.started", is(12.45)))
			.andExpect(jsonPath("$.balance", is(3.11)));
        // @formatter:on

	}
	
	@Test
	void whenFindById_thenReturnResponseStatusNotFound() throws Exception {
		
		when(accountService.findById(any(Long.class))).thenThrow(accountNotFound);
		
		get("/accounts/999").andExpect(status().isNotFound());
		
	}
	
	/**
	 * methods findAll
	 */

	@Test
	void whenFindAll_thenReturnNoContent() throws Exception {

		Page<Account> page = new PageImpl<>(Collections.emptyList());

		when(accountService.findAll(any())).thenReturn(page);

		get("/accounts").andExpect(status().isNoContent());

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusOk() throws Exception {

		Account energy = createAccount(3l, new BigDecimal("12.45"), new BigDecimal("3.11"));

		Page<Account> page = new PageImpl<>(Arrays.asList(energy));

		when(accountService.findAll(any())).thenReturn(page);

		// @formatter:off
		get("/accounts")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk());
        // @formatter:on

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusPartialContent() throws Exception {

		Account energy = createAccount(3l, new BigDecimal("12.45"), new BigDecimal("3.11"));
		
		Page<Account> page = new PageImpl<>(Arrays.asList(energy), PageRequest.of(1, 8), 20);

		when(accountService.findAll(any())).thenReturn(page);

		// @formatter:off		
		get("/accounts?size=2")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isPartialContent());
        // @formatter:on

	}
	
	/**
	 * methods create
	 */

	void whenCreateWithoutBody_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithNullName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithNullBalance_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidStarted_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidBalance_thenReturnResponseStatusBadRequest() throws Exception { }

	@Test
	void whenCreateWithParameterStartedIsNull_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Account entity = createAccount(3l, new BigDecimal("11.23"), new BigDecimal("11.23"));
		when(accountService.create(any())).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "Post Test Account")
			.add("balance", new BigDecimal("11.23"));

		post("/accounts")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/accounts/3"))
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(11.23)));
        // @formatter:on
		
	}
	
	@Test
	void whenCreate_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Account entity = createAccount(3l, new BigDecimal("11.23"), new BigDecimal("123.23"));
		when(accountService.create(any())).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "Post Test Account")
			.add("started", new BigDecimal("11.23"))
			.add("balance", new BigDecimal("123.23"));

		post("/accounts")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/accounts/3"))
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(123.23)));
        // @formatter:on
		
	}
	
	/**
	 * methods update
	 */
	
	void whenUpdateWithoutId_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithoutBody_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullStater_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullBalance_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidStarted_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidBalance_thenReturnResponseStatusBadRequest() throws Exception { }

	@Test
	void whenUpdate_thenReturnEntityObject() throws Exception {

		Account entity = createAccount(3l, new BigDecimal("11.23"), new BigDecimal("123.23"));
		when(accountService.update(any(Long.class), any(Account.class))).thenReturn(entity);

		// @formatter:off
		body()
			.add("started", new BigDecimal("11.23"))
			.add("balance", new BigDecimal("123.23"));

		put("/accounts/3")
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(123.23)));
        // @formatter:on
		
	}
	
	/**
	 * methods delete
	 */

	@Test
	void whenDeleteWithInvalidId_thenReturnResponseStatusNotFound() throws Exception {
		
		doThrow(accountNotFound).when(accountService).delete(any(Long.class));
		
		delete("/accounts/999").andExpect(status().isNotFound());
		
	}
	
	@Test
	void whenDelete_thenReturnEntityObject() throws Exception {
		
		doNothing().when(accountService).delete(any(Long.class));
		
		delete("/accounts/1").andExpect(status().isOk());
		
	}

}
