package com.restaurant.manager.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailService {
	UserDetails loadUserById(int id);
}
