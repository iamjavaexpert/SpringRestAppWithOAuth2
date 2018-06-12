package com.spring.rest.sample.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.rest.sample.model.User;
import com.spring.rest.sample.model.UserRole;
import com.spring.rest.sample.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : userRepository.findAll()) {
			users.add(user);
		}
		return users;
	}

	@Override
	public User addUpdateUser(User user) {
		if (user != null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		return user != null ? userRepository.save(user) : user;
	}

	@Override
	public User findUserById(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 *
	 * set up a default user with two roles USER and ADMIN
	 *
	 */
	@PostConstruct
	private void setupDefaultUser() {
		// -- just to make sure there is an ADMIN user exist in the database for
		// testing purpose
		if (userRepository.count() == 0) {
			userRepository.save(new User("admin", passwordEncoder.encode("admin"),
					Arrays.asList(new UserRole("USER"), new UserRole("ADMIN"))));
		}
	}

}
