package com.cropaccounting.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.Crop;

public interface CropRepository extends PagingAndSortingRepository<Crop, Long> {
	@Query("SELECT c FROM Crop c WHERE c.farmer.nid = :nid AND c.startDate > :startDate")
	public List<Crop> find(@Param("nid") String nid, @Param("startDate") LocalDate startDate);
	
	public List<Crop> findAll();
}
