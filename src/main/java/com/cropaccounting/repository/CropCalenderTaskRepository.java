package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.CropCalenderTask;

public interface CropCalenderTaskRepository extends CrudRepository<CropCalenderTask, Long> {
	/*
	@Query("SELECT c FROM CropCalenderTask c WHERE c.cropActivityType.name = :type AND c.crop = :crop AND c.varity = :varity")
	public Optional<CropCalenderTask> find(@Param("type") String type, @Param("crop") long crop, @Param("varity") long varity);
	*/
}
