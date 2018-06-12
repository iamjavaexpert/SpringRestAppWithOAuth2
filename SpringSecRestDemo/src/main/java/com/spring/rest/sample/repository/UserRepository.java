package com.spring.rest.sample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.rest.sample.model.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	User findUserByUsername(String username);
}
