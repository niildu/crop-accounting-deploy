package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.Division;

public interface DivisionRepository  extends CrudRepository<Division, Long>  {
	@Query("SELECT d FROM Division d WHERE d.portalId = :portalId")
	public Optional<Division> getDivisionByPortalId(@Param("portalId") long portalId);
}
