package com.spring.rest.sample.service;

import java.util.List;

import com.spring.rest.sample.model.User;

public interface UserService {
	public List<User> getUsers();

	public User addUpdateUser(User user);

	public User findUserById(Long userId);

	public void deleteUserById(Long userId);

	public void deleteUser(User user);

	public User getUserByUsername(String username);
}
