package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.FarmerTask;

public interface FarmerTaskRepository extends PagingAndSortingRepository<FarmerTask, Long> {
	public List<FarmerTask> findAll();
}
