package com.cropaccounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.District;

public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {
	@Query("SELECT d FROM District d WHERE d.portalId = :portalId")
	public Optional<District> getDistrictByPortalId(@Param("portalId") long portalId);
	public List<District> findAll();
}
