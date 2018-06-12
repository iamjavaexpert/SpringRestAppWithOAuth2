package com.spring.rest.sample.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutSuccessHandler {

	// @Resource(name = "tokenServices")
	// ConsumerTokenServices tokenServices;

	@Override
	public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		// tokenServices.revokeToken(tokenValue);
		res.sendRedirect("login");
	}

}
