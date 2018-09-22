package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.District;

public interface DistrictRepository extends CrudRepository<District, Long> {
	@Query("SELECT d FROM District d WHERE d.portalId = :portalId")
	public Optional<District> getDistrictByPortalId(@Param("portalId") long portalId);
}
