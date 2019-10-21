package com.am.sort.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.sort.api.security.entity.ChangeStatus;


public interface ChangeStatusRepository extends MongoRepository<ChangeStatus, String>{
	
	Iterable<ChangeStatus> findByEmbarqueIdOrderByDateChangeStatusDesc(String embarqueId);
	
	Iterable<ChangeStatus> findByClientIdOrderByDateChangeStatusDesc(String clientId);
	

}
