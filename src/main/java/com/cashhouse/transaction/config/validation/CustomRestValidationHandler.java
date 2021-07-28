package com.cashhouse.transaction.config.validation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cashhouse.transaction.dto.response.ResponseEntityErrorDto;

@RestControllerAdvice
public class CustomRestValidationHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ResponseEntityErrorDto> handle(MethodArgumentNotValidException exception) {
		
		List<ResponseEntityErrorDto> errors = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ResponseEntityErrorDto error = new ResponseEntityErrorDto(e.getField(), message);
			
			errors.add(error);
		});
		
		return errors;
		
	}
	
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntityErrorDto handle(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntityErrorDto(e.getMethod(), e.getLocalizedMessage());
    }
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntityErrorDto handle(EntityNotFoundException exception) {
		
		Object[] args = {};
		String message = messageSource.getMessage(exception.getMessage(), args, LocaleContextHolder.getLocale());
		return new ResponseEntityErrorDto("Request parameter", message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntityErrorDto handle(IllegalStateException exception) {

		Object[] args = {};
		String message = messageSource.getMessage(exception.getMessage(), args, LocaleContextHolder.getLocale());
		return new ResponseEntityErrorDto("Illegal state", message);
		
	}

}
