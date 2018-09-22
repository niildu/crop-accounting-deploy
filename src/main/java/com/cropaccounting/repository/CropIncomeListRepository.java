package com.cropaccounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.CropIncomeList;

public interface CropIncomeListRepository extends CrudRepository<CropIncomeList, Long> {

	@Query("SELECT c FROM CropIncomeList c WHERE c.crop = :crop AND c.varity = :varity")
	public List<CropIncomeList> find(@Param("crop") long crop, @Param("varity") long varity);
	
	default Optional<CropIncomeList> findOne(@Param("crop") long crop, @Param("varity") long varity) {
		return find(crop, varity).stream().findFirst();
	}
}
