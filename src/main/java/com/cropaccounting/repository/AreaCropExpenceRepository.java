package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.AreaCropExpence;

public interface AreaCropExpenceRepository extends CrudRepository<AreaCropExpence, Long> {
	@Query("SELECT c FROM AreaCropExpence c WHERE c.crop = :crop AND c.varity = :varity"
			+ " AND c.division.id = :divisionId AND c.district.id = :districtId"
			+ "	AND c.subDistrict.id = :subDistrictId")
	public Optional<AreaCropExpence> findAreaExpence(@Param("crop") long crop,
			@Param("varity") long varity, @Param("divisionId") long divisionId,
			@Param("districtId") long districtId, @Param("subDistrictId") long subDistrictId);
}
