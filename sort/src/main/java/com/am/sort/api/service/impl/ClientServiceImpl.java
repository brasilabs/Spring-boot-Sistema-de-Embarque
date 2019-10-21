package com.am.sort.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.am.sort.api.repository.ChangeStatusRepository;
import com.am.sort.api.repository.ClientRepository;
import com.am.sort.api.security.entity.ChangeStatus;
import com.am.sort.api.security.entity.Client;
import com.am.sort.api.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ChangeStatusRepository changeStatusRepository;

	@Override
	public Client createOrUpdate(Client client) {
		return this.clientRepository.save(client);
	}

	@Override
	public Client findById(String id) {
		return this.clientRepository.findOne(id);
	}

	@Override
	public void delete(String id) {
		this.clientRepository.delete(id);
		
	}

	@Override
	public Page<Client> ListClient(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository.findAll(pages);
	}

	@Override
	public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
		return this.changeStatusRepository.save(changeStatus);
	}

	@Override
	public Iterable<ChangeStatus> listChangeStatus(String clientId) {
		return this.changeStatusRepository.findByEmbarqueIdOrderByDateChangeStatusDesc(clientId);
	}

	@Override
	public Page<Client> findByCurrentUser(int page, int count, String userId) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository.findByUserIdOrderByDateDesc(pages, userId);
	}

	@Override
	public Page<Client> findByParameters(int page, int count, String cnpjCpf, String status, String razao) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository
				.findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoOrderByDateDesc(
						cnpjCpf, status, razao, pages);
	}

	@Override
	public Page<Client> findByParametersAndCurrentUser(int page, int count, String cnpjCpf, String status, String razao,
			String userId) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository
				.findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoIgnoreCaseContainingAndUserIdOrderByDateDesc(
						cnpjCpf, status, razao, userId, pages);
	}

	@Override
	public Page<Client> findByNumer(int page, int count, Integer numer) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository.findByNumer(numer, pages);
	}

	@Override
	public Iterable<Client> findAll() {
		return this.clientRepository.findAll();
	}

	@Override
	public Page<Client> findByParametersAndAssignedUser(int page, int count, String cnpjCpf, String status,
			String razao, String assignedUserId) {
		Pageable pages = new PageRequest(page, count);
		return this.clientRepository
				.findByCnpjCpfIgnoreCaseContainingAndStatusIgnoreCaseContainingAndRazaoIgnoreCaseContainingAndAssignedUserIdOrderByDateDesc(
						cnpjCpf, status, razao, assignedUserId, pages);
	}

		
}
