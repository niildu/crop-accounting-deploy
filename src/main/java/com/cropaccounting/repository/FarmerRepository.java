package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.Farmer;

public interface FarmerRepository extends CrudRepository<Farmer, Long> {
	
	@Query("SELECT f FROM Farmer f WHERE f.nid = :nid")
	public Farmer findByNID(@Param("nid") String nid);
	
	@Query("SELECT f FROM Farmer f WHERE f.nid like :nid%")
	public List<Farmer> searchByNID(@Param("nid") String nid);
	
	@Query("SELECT f FROM Farmer f WHERE f.mobileNo like %:mobileNo")
	public List<Farmer> searchByMobile(@Param("mobileNo") String mobileNo);
}
