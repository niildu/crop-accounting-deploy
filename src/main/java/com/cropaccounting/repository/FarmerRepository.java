package com.cropaccounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.Farmer;

public interface FarmerRepository extends PagingAndSortingRepository<Farmer, Long> {
	
	@Query("SELECT f FROM Farmer f WHERE f.nid = :nid")
	public Farmer findByNID(@Param("nid") String nid);
	
	@Query("SELECT f FROM Farmer f WHERE f.nid like :nid%")
	public List<Farmer> searchByNID(@Param("nid") String nid);

	@Query("SELECT f FROM Farmer f WHERE f.nid like :nid%")
	public Page<Farmer> searchByNID(@Param("nid") String nid, Pageable pageable);

	@Query("SELECT f FROM Farmer f WHERE f.mobileNo like %:mobileNo")
	public List<Farmer> searchByMobile(@Param("mobileNo") String mobileNo);
	
	@Query("SELECT f FROM Farmer f WHERE f.mobileNo like %:mobileNo")
	public List<Farmer> searchByMobile(@Param("mobileNo") String mobileNo, Pageable pageable);
	
	public List<Farmer> findAll();
}
