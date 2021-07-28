package com.cashhouse.transaction.dto.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableDto<T> {
	
	public Page asPage(Page<T> list, Pageable pageable);

}
