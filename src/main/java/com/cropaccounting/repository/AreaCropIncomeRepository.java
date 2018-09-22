package com.cropaccounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.area.AreaCropIncome;

public interface AreaCropIncomeRepository extends CrudRepository<AreaCropIncome, Long> {
	@Query("SELECT c FROM AreaCropIncome c WHERE c.crop = :crop AND c.varity = :varity"
			+ " AND c.division.id = :divisionId AND c.district.id = :districtId"
			+ "	AND c.subDistrict.id = :subDistrictId")
	public Optional<AreaCropIncome> findAreaCropIncome(@Param("crop") long crop,
			@Param("varity") long varity, @Param("divisionId") long divisionId,
			@Param("districtId") long districtId, @Param("subDistrictId") long subDistrictId);
}
