package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropIncome;

public interface CropIncomeRepository extends PagingAndSortingRepository<CropIncome, Long>  {
	public List<CropIncome> findAll();
}
