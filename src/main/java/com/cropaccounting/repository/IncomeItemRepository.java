package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.IncomeItem;

public interface IncomeItemRepository extends PagingAndSortingRepository<IncomeItem, Long>  {
	public List<IncomeItem> findAll();
}
