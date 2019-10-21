package com.am.sort.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.sort.api.security.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmail(String email);

}
