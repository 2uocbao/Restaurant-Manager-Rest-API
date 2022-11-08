package com.restaurant.manager.sercurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class hashingwithBCrypt {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean matchedPassword(String password, String encodePassword) {
		return passwordEncoder.matches(password, encodePassword);
	}
}
