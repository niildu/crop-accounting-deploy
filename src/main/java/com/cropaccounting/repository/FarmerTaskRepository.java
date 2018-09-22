package com.cropaccounting.repository;

import org.springframework.data.repository.CrudRepository;

import com.cropaccounting.models.FarmerTask;

public interface FarmerTaskRepository extends CrudRepository<FarmerTask, Long> {

}
