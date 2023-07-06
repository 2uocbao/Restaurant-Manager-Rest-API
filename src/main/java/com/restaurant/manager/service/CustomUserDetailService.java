package com.restaurant.manager.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.restaurant.manager.request.LoginRequest;

public interface CustomUserDetailService {
	UserDetails loadUserById(int id);
	
	public String login(LoginRequest loginRequest);
}
