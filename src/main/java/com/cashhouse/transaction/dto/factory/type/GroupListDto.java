package com.cashhouse.transaction.dto.factory.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cashhouse.transaction.dto.factory.PageableDto;

public abstract class GroupListDto<T, L> implements PageableDto<L> {
	
	private Map<String, Collection<T>> dtoMap = new LinkedHashMap<>();
	
	void add(T t, String header) {
		dtoMap.computeIfAbsent(header, 
				k -> new LinkedList<T>()).add(t);
	}
	
	public abstract String getHeader(L l);
	
	public abstract T getContent(L l);
	
	@Override
	public Page<?> asPage(Page<L> list, Pageable pageable){
		
		dtoMap.clear();

		list.forEach(l -> {
			String header = getHeader(l);
			T content = getContent(l);
			add(content, header);
		});
		
		Long totalElements = list.getTotalElements();
		List<Map<String, Collection<T>>> asList = Arrays.asList(dtoMap);
		
		return new PageImpl<>(asList, pageable, totalElements.intValue());
		
	}

}
