package com.restaurant.manager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.LoginRequest;
import com.restaurant.manager.service.CustomUserDetailService;

@RestController
@RequestMapping("/authen")
public class AuthenController {

	@Autowired
	CustomUserDetailService customUserDetailService;

	@PostMapping("/singin")
	ResponseEntity<String> singinUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(customUserDetailService.login(loginRequest));
	}
}
