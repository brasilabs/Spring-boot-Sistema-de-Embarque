package com.am.sort.api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.am.sort.api.security.entity.ChangeStatus;
import com.am.sort.api.security.entity.Client;

@Component
public interface ClientService {

	Client createOrUpdate(Client client);

	Client findById(String id);

	void delete(String id);

	Page<Client> ListClient(int page, int count);

	ChangeStatus createChangeStatus(ChangeStatus changeStatus);

	Iterable<ChangeStatus> listChangeStatus(String clientId);
	
	Page<Client> findByCurrentUser(int page, int count, String userId);
	
	Page<Client> findByParameters(int page, int count, String cnpjCpf, String status, String razao);
	
	Page<Client> findByParametersAndCurrentUser(int page, int count, String cnpjCpf, String status, String razao, String userId);
	
	Page<Client> findByNumer(int page, int count, Integer numer);
	
	Iterable<Client> findAll();
	
	public Page<Client> findByParametersAndAssignedUser(int page, int count, String cnpjCpf, String status, String razao, String assignedUserId);

	

}
