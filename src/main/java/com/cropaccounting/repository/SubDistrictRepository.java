package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.SubDistrict;

public interface SubDistrictRepository extends CrudRepository<SubDistrict, Long> {
	@Query("SELECT d FROM SubDistrict d WHERE d.portalId = :portalId")
	public Optional<SubDistrict> getSubDistrictByPortalId(@Param("portalId") long portalId);
}
