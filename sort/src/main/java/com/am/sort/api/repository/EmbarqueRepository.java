package com.am.sort.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.sort.api.security.entity.Embarque;

public interface EmbarqueRepository extends MongoRepository<Embarque, String>{
	
	
	Page<Embarque> findByUserIdOrderByDateDesc(Pageable pages, String UserId);
	
	Page<Embarque> findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeOrderByDateDesc(
			String rota, String client, String status, String prioridade, Pageable pages);
	
	Page<Embarque> findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUserIdOrderByDateDesc(
			String rota, String client, String status, String prioridade, String userId, Pageable pages);
	
	
	Page<Embarque> findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndAssignedUserIdOrderByDateDesc(
			String rota, String client, String status, String prioridade, String assignedUserId, Pageable pages);
	
	
	Page<Embarque> findByNumero(Integer numero, Pageable pages);

}
