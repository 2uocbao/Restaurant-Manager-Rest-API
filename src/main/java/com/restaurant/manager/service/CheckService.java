package com.restaurant.manager.service;

public interface CheckService {
	boolean isValidEmail(String email);

	boolean checkPhone(String phone);

	boolean checkName(String name);
	
	boolean checkCode(String code);
}
