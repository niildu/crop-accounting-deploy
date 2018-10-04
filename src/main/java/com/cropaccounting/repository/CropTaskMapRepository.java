package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropTaskMap;

public interface CropTaskMapRepository extends PagingAndSortingRepository<CropTaskMap, Long>  {
	public List<CropTaskMap> findAll();
}
