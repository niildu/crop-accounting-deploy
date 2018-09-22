package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.FarmerCropTask;

public interface FarmerCropTaskRepository extends CrudRepository<FarmerCropTask, Long> {
	@Query("SELECT f FROM FarmerCropTask f where f.farmer.nid = :nid")
	public List<FarmerCropTask> findByNID(@Param("nid") String nid);
}
