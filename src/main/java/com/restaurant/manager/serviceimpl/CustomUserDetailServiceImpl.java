package com.restaurant.manager.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.jwt.JwtTokenProvider;
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.request.LoginRequest;
import com.restaurant.manager.service.CustomUserDetailService;

@Service
@Transactional
public class CustomUserDetailServiceImpl implements CustomUserDetailService, UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.getEmployeeByPhone(username);
		if (employee == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(employee.getPhone(), employee.getPassword(), employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).toList());
		}

	@Override
	public UserDetails loadUserById(int id) {
		Employee employee = employeeRepository.detailEmployee(id);
		return new User(employee.getPhone(), employee.getPassword(), employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).toList());
	}

	@Override
	public String login(LoginRequest loginRequest) {
		Employee employee = employeeRepository.getEmployeeByPhone(loginRequest.getUsername());
		return jwtTokenProvider.generateToken(new User(employee.getPhone(), employee.getPassword(), employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).toList()));
	}

}
