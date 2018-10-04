package com.cropaccounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cropaccounting.models.CropExpenceList;

public interface CropExpenceListRepository extends PagingAndSortingRepository<CropExpenceList, Long>  {
	@Query("SELECT c FROM CropExpenceList c WHERE c.type = :type AND c.crop = :crop AND c.varity = :varity")
	public Optional<CropExpenceList> find(@Param("type") String type, @Param("crop") long crop, @Param("varity") long varity);
	
	@Query("SELECT c FROM CropExpenceList c WHERE c.crop = :crop AND c.varity = :varity")
	public Optional<CropExpenceList> find(@Param("crop") long crop, @Param("varity") long varity);
	
	public List<CropExpenceList> findAll();
}
