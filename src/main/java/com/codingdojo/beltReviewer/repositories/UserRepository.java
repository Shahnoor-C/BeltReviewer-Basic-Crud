package com.codingdojo.beltReviewer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.beltReviewer.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
}


