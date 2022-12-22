package com.restaurant.manager.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.service.EmployeeService;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	EmployeeService employeeService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	UserDetails isValidUser(String username, String password) {
		Employee employee = employeeService.getEmployeeByPhone(username);
		if (username.equalsIgnoreCase(employee.getPhone())
				&& passwordEncoder.matches(password, employee.getPassword())) {
			return User.withUsername(username).password("NOT_DISCLOSED").roles(employee.getRole()).build();
		}
		return null;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = isValidUser(username, password);

		if (userDetails != null) {
			return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Incorrect user credentials !!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
