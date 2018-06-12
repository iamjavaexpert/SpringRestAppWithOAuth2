package com.spring.rest.sample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.rest.sample.model.User;
import com.spring.rest.sample.repository.UserRepository;

@Service
public class CustomUSerDetailsService implements UserDetailsService {

	@Autowired
	UserRepository UserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = UserRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}

		return new CustomUserDetails(user);
	}

}
