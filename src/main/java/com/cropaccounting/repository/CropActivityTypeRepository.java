package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropActivityType;

public interface CropActivityTypeRepository extends PagingAndSortingRepository<CropActivityType, Long> {
	public List<CropActivityType> findAll();
}
