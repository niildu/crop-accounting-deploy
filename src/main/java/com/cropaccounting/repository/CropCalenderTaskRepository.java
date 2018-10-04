package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.CropCalenderTask;

public interface CropCalenderTaskRepository extends PagingAndSortingRepository<CropCalenderTask, Long> {
	/*
	@Query("SELECT c FROM CropCalenderTask c WHERE c.cropActivityType.name = :type AND c.crop = :crop AND c.varity = :varity")
	public Optional<CropCalenderTask> find(@Param("type") String type, @Param("crop") long crop, @Param("varity") long varity);
	*/
	public List<CropCalenderTask> findAll();
}
