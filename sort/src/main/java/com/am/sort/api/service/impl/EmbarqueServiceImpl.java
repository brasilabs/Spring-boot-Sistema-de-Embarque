package com.am.sort.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.am.sort.api.repository.ChangeStatusRepository;
import com.am.sort.api.repository.EmbarqueRepository;
import com.am.sort.api.security.entity.ChangeStatus;
import com.am.sort.api.security.entity.Embarque;
import com.am.sort.api.service.EmbarqueService;

@Service
public class EmbarqueServiceImpl implements EmbarqueService{
	
	@Autowired
	private EmbarqueRepository embarqueRepository;

	@Autowired
	private ChangeStatusRepository changeStatusRepository;


	@Override
	public Embarque createOrUpdate(Embarque embarque) {
		return this.embarqueRepository.save(embarque);
	}

	@Override
	public Embarque findById(String id) {
		return this.embarqueRepository.findOne(id);
	
	}

	@Override
	public void delete(String id) {
		this.embarqueRepository.delete(id);
		
	}

	@Override
	public Page<Embarque> listEmbarque(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository.findAll(pages);
	}

	@Override
	public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
		return this.changeStatusRepository.save(changeStatus);
	}

	@Override
	public Iterable<ChangeStatus> listChangeStatus(String embarqueId) {
		return this.changeStatusRepository.findByEmbarqueIdOrderByDateChangeStatusDesc(embarqueId);
	}

	@Override
	public Page<Embarque> findByCurrentUser(int page, int count, String userId) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository.findByUserIdOrderByDateDesc(pages, userId);
	}

	
	public Page<Embarque> findByParameters(int page, int count,String rota, String client, String status, String prioridade) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository
				.findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeOrderByDateDesc(
						rota, client, status, prioridade, pages);
	}

	@Override
	public Page<Embarque> findByParametersAndCurrentUser(int page, int count, String rota, String client, String status, String prioridade, String userId) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository
				.findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUserIdOrderByDateDesc(
						rota, client, status, prioridade, userId, pages);
	}

	@Override
	public Page<Embarque> findByNumber(int page, int count, Integer number) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository.findByNumero(number, pages);
	}

	@Override
	public Iterable<Embarque> findAll() {
		return this.embarqueRepository.findAll();
	}

	@Override
	public Page<Embarque> findByParametersAndAssignedUser(int page, int count, String rota, String client, String status, String prioridade, String assignedUserId) {
		Pageable pages = new PageRequest(page, count);
		return this.embarqueRepository
				.findByRotaIgnoreCaseContainingAndClientIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndAssignedUserIdOrderByDateDesc(
						rota, client, status, prioridade, assignedUserId, pages);
	}

}