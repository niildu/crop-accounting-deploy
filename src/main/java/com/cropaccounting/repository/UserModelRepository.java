package com.cropaccounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cropaccounting.models.UserModel;

public interface UserModelRepository extends PagingAndSortingRepository<UserModel, Long> {
	
	@Query("SELECT u from UserModel u WHERE u.id <> 1")
	public List<UserModel> getUserExceptAdmin();
	
	public List<UserModel> findAll();
}
