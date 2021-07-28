package com.cashhouse.transaction.validation;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashhouse.transaction.config.validation.CustomRestValidationHandler;
import com.cashhouse.transaction.util.SampleRequest;

import lombok.Getter;
import lombok.Setter;

@WebMvcTest(CustomRestValidationHandlerTest.SampleController.class)
@ContextConfiguration(classes = { CustomRestValidationHandlerTest.SampleController.class,
		CustomRestValidationHandler.class })
@AutoConfigureMockMvc
class CustomRestValidationHandlerTest extends SampleRequest {

	@MockBean
	private MessageSource messageSource;

	@Test
	public void whenResponseBodyInlid_thenReturnBadRequest() throws Exception {

		when(messageSource.getMessage(any(MessageSourceResolvable.class), any(Locale.class)))
				.thenReturn("must not be empty");

		// @formatter:off
		body().add("value", "");
		
		post("/exception/bean/validator")
			      	.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$[0].field", is("value")))
					.andExpect(jsonPath("$[0].message", is("must not be empty")));
		// @formatter:on

	}

	@Test
	public void whenResourceVerbInvalid_thenReturnMethodNotAllowed() throws Exception {

		// @formatter:off
		body();
		post("/exception/transaction_invalid/status")
			      	.andExpect(status().isMethodNotAllowed())
					.andExpect(jsonPath("$.field", is("POST")))
					.andExpect(jsonPath("$.message", is("Request method 'POST' not supported")));
		// @formatter:on

	}

	@Test
	public void whenEntityNotFound_thenReturnNotFound() throws Exception {

		when(messageSource.getMessage(any(String.class), any(Object[].class), any())).thenReturn("entity not found");

		// @formatter:off
		body();
		get("/exception/not_found")
			      	.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.field", is("Request parameter")))
					.andExpect(jsonPath("$.message", is("entity not found")));
		// @formatter:on

	}

	@Test
	public void whenStateInvalid_thenReturnBadRequest() throws Exception {

		when(messageSource.getMessage(any(String.class), any(Object[].class), any()))
				.thenReturn("Invalid State Operation");

		// @formatter:off
		body();
		get("/exception/transaction_invalid/status")
			      	.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.field", is("Illegal state")))
					.andExpect(jsonPath("$.message", is("Invalid State Operation")));
		// @formatter:on

	}

	@RestController
	@RequestMapping("/api/v1/exception")
	public static class SampleController {

		@PostMapping("/bean/validator")
		public void throwMethodArgumentNotValidException(@RequestBody @Valid SimpleBean propertie) throws Exception {
		}

		@GetMapping("/transaction_invalid/status")
		public void throwInvalidOperationException() throws Exception {
			throw new IllegalStateException("status.invalid");
		}

		@GetMapping("/not_found")
		public void throwEntityNotFoundException_Class() throws Exception {
			throw new EntityNotFoundException("entity.not.found");
		}

	}

	@Getter
	@Setter
	public static class SimpleBean {
		@NotEmpty
		String value;
	}

}
