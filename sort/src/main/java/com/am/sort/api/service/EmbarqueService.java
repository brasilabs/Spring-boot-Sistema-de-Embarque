package com.am.sort.api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.am.sort.api.security.entity.ChangeStatus;
import com.am.sort.api.security.entity.Embarque;

@Component
public interface EmbarqueService {
	
	Embarque createOrUpdate(Embarque embarque);
	
	Embarque findById(String id);
	
	void delete(String id);
	
	Page<Embarque> listEmbarque(int page, int count);
	
	ChangeStatus createChangeStatus(ChangeStatus changeStatus);
	
	Iterable<ChangeStatus> listChangeStatus(String embarqueId);
	
	Page<Embarque> findByCurrentUser(int page, int count, String userId);
	
	Page<Embarque> findByParameters(int page, int count, String rota, String cliente, String status, String prioridade);
	
	Page<Embarque> findByParametersAndCurrentUser(int page, int count, String rota, String cliente, String status, String prioridade, String userId);
	
	Page<Embarque> findByNumber(int page, int count,Integer number);
	
	Iterable<Embarque> findAll();
	
	public Page<Embarque> findByParametersAndAssignedUser(int page, int count, String rota, String cliente, String status, String prioridade, String assignedUserId);
}
