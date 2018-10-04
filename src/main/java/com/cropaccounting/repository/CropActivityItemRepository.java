package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropActivityItem;

public interface CropActivityItemRepository extends PagingAndSortingRepository<CropActivityItem, Long>  {
	
	public List<CropActivityItem> findAll();
}
