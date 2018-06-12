package com.spring.rest.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.sample.model.User;
import com.spring.rest.sample.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Resource(name = "tokenServices")
	ConsumerTokenServices tokenServices;

	/**
	 * revoke existing active access token
	 *
	 * @param tokenId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/token/revoke/{tokenId}")
	public ResponseEntity<String> revokeToken(@PathVariable("tokenId") String tokenId) {
		if (tokenServices.revokeToken(tokenId)) {
			return new ResponseEntity<String>(tokenId, HttpStatus.OK);
		}

		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	/**
	 * get the list of all users
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers() {
		return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.CREATED);
	}

	/**
	 * add user into the database
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		System.out.println("Total Roles:" + user.getRoles().size());
		User newUser = null;
		try {
			newUser = userService.addUpdateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	/**
	 * get user by userId
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/users/userbyId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
		User user = userService.findUserById(userId);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	/**
	 * update user
	 *
	 * @param userId
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
		user.setId(userId);
		User newUser = userService.addUpdateUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	/**
	 * delete user
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployees(@PathVariable("userId") Long userId) {
		userService.deleteUserById(userId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * get user by username and password
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> user(@PathVariable("username") String username) {
		User user = userService.getUserByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * get authorization code to get access token
	 *
	 * @param session
	 * @param code
	 * @return
	 */
	@RequestMapping("/state/new")
	public Map<String, Object> newState(HttpSession session, @RequestParam("code") String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.TRUE);

		String state = UUID.randomUUID().toString();
		result.put("state", state);
		result.put("code", code);
		session.setAttribute("state", state);

		return result;
	}

	/**
	 * Home page after user login
	 *
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "<p>Welcome!</p>" + "<p><a href='logout'>Logout</a></p>"
				+ "<a href='oauth/authorize?client_id=secure-client&response_type=code&redirect_uri=http://localhost:8080/state/new' target='_blank'>Get Authorization Code</a>"
				+ "</p>";
	}
}
