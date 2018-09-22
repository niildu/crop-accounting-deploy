package com.cropaccounting.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;

import com.cropaccounting.models.Varieties;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface VarietiesRepository extends CrudRepository<Varieties, Long>  {

}
