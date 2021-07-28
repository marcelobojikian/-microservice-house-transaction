package com.cashhouse.transaction.dto.request.account;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccount {

	@NotBlank
	String name;

}
