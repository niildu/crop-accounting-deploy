package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cropaccounting.models.Crops;

public interface CropsRepository extends CrudRepository<Crops, Long> {
	public List<Crops> findAll();
}
