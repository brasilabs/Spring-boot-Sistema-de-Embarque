package com.am.sort.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.am.sort.api.repository.UserRepository;
import com.am.sort.api.security.entity.User;
import com.am.sort.api.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public User createOrUpdate(User user) {
		return this.userRepository.save(user);
	}

	public User findById(String id) {
		return this.userRepository.findOne(id);
	}

	public void delete(String id) {
		this.userRepository.delete(id);
	}

	public Page<User> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.userRepository.findAll(pages);
	}
}
