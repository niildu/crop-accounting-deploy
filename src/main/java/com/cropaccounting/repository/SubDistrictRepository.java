package com.cropaccounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.SubDistrict;

public interface SubDistrictRepository extends PagingAndSortingRepository<SubDistrict, Long> {
	@Query("SELECT d FROM SubDistrict d WHERE d.portalId = :portalId")
	public Optional<SubDistrict> getSubDistrictByPortalId(@Param("portalId") long portalId);
	
	public List<SubDistrict> findAll();
}
