package com.cashhouse.transaction.dto.factory.type;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cashhouse.transaction.dto.factory.PageableDto;

public abstract class SimpleListDto<T, L> implements PageableDto<L> {

	public abstract T getContent(L l);

	@Override
	public Page<?> asPage(Page<L> list, Pageable pageable) {
		List<T> dtoList = new ArrayList<>();
		list.forEach(l -> dtoList.add(getContent(l)));

		Long totalElements = list.getTotalElements();
		return new PageImpl<>(dtoList, pageable, totalElements.intValue());
	}

}
