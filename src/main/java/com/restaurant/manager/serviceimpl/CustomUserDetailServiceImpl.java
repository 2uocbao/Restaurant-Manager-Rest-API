package com.restaurant.manager.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.security.UserPrincipal;
import com.restaurant.manager.service.CustomUserDetailService;

@Service
@Transactional
public class CustomUserDetailServiceImpl implements CustomUserDetailService, UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.getEmployeeByPhone(username);
		if (employee == null) {
			throw new UsernameNotFoundException(username);
		}
		return UserPrincipal.create(employee);
	}

	@Override
	public UserDetails loadUserById(int id) {
		Employee employee = employeeRepository.detailEmployee(id);
		return UserPrincipal.create(employee);
	}
}
