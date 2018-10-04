package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.CropActivity;
import com.cropaccounting.models.ExpenceItem;

public interface ExpenceItemRepository extends PagingAndSortingRepository<ExpenceItem, Long>  {
	@Query("SELECT expenceItem FROM ExpenceItem expenceItem ORDER BY id DESC")
	public List<ExpenceItem> findOrderedList();
	
	@Query("SELECT expenceItem FROM ExpenceItem expenceItem where expenceItem.cropActivity = :cropActivity ORDER BY id DESC")
	public List<ExpenceItem> searchExpenceItem(@Param("cropActivity") CropActivity cropActivity);
	
	public List<ExpenceItem> findAll();
}
