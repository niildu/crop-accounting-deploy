package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropActivity;

public interface CropActivityRepository extends PagingAndSortingRepository<CropActivity, Long>  {

	public List<CropActivity> findAll();
}
