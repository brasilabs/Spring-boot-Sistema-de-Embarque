package com.am.sort.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.sort.api.security.entity.Client;

public interface ClientRepository extends MongoRepository<Client, String>{
	
	Page<Client> findByUserIdOrderByDateDesc(Pageable pages, String UserId);	

	Page<Client> findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoOrderByDateDesc(
				 String cnpjCpf, String status, String razao, Pageable pages);
	
	Page<Client> findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoIgnoreCaseContainingAndUserIdOrderByDateDesc(
			 String cnpjCpf, String status, String razao, String userId, Pageable pages);
	
	Page<Client> findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoIgnoreCaseContainingAndAssignedUserIdOrderByDateDesc(
			 String cnpjCpf, String status, String razao, String assignedUserId, Pageable pages);
	
	Page<Client> findByNumer(Integer numer, Pageable pages);
}
