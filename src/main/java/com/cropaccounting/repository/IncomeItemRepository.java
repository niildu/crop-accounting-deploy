package com.cropaccounting.repository;

import org.springframework.data.repository.CrudRepository;

import com.cropaccounting.models.IncomeItem;

public interface IncomeItemRepository extends CrudRepository<IncomeItem, Long>  {

}
