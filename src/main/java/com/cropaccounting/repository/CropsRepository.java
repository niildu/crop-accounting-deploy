package com.cropaccounting.repository;

import org.springframework.data.repository.CrudRepository;

import com.cropaccounting.models.Crops;

public interface CropsRepository extends CrudRepository<Crops, Long> {

}
