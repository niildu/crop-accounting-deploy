package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropIncomeItem;

public interface CropIncomeItemRepository extends PagingAndSortingRepository<CropIncomeItem, Long>  {
	public List<CropIncomeItem> findAll();
}
