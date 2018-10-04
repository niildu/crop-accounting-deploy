package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.Varieties;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface VarietiesRepository extends PagingAndSortingRepository<Varieties, Long>  {
	public List<Varieties> findAll();
}
