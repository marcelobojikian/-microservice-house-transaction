package com.cashhouse.transaction.dto.factory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.cashhouse.transaction.dto.factory.type.GroupListDto;
import com.cashhouse.transaction.dto.factory.type.SimpleListDto;

public abstract class ResponseListDtoFactory<T, L> {
	
	private HashMap<String, GroupListDto<T, L>> fields;
	private SimpleListDto<T, L> defaultList;
	
	protected ResponseListDtoFactory(SimpleListDto<T, L> defaultList) {
		this.defaultList = defaultList;
		fields = new HashMap<>();
	}
	
	public void addField(String field, GroupListDto<T, L> groupList) {
		fields.put(field, groupList);
	}
	
	public PageableDto<L> getListDto(Pageable pageable) {
		return getListDto(pageable.getSort());
	}
	
	public PageableDto<L> getListDto(Sort sort) {
		
		Set<String> keys = fields.keySet();
		Iterator<String> keyIterator = keys.iterator();
		
		while (keyIterator.hasNext()) {
			String type = keyIterator.next();
			Order order = sort.getOrderFor(type);
			
			if(order != null) {
				return fields.get(order.getProperty());
			}
		}
		
		return defaultList;
		
	}

}
