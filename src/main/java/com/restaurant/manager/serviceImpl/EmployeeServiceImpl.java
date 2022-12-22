package com.restaurant.manager.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
	@Autowired
	EmployeeRepository employeeRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public boolean createEmployee(Employee employee) {
		String pass = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(pass);
		return employeeRepository.createEmployee(employee);
	}

	@Override
	public Employee detailEmployee(String id) {
		return employeeRepository.detailEmployee(id);
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		return employeeRepository.updateEmployee(employee);
	}

	@Override
	public boolean deleteEmployee(String id) {
		return employeeRepository.deleteEmployee(id);
	}

	@Override
	public boolean loginEmployee(String phone, String password) {
		String passwordDB = employeeRepository.getPasswordByPhone(phone);
		return passwordEncoder.matches(password, passwordDB);
	}

	@Override
	public List<Employee> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId) {
		return employeeRepository.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
	}

	@Override
	public Employee getEmployeeByPhone(String phone) throws UsernameNotFoundException {
		return employeeRepository.getEmployeeByPhone(phone);
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.getEmployeeByEmail(email);
	}

	@Override
	public boolean changePasswordEmployee(String id, String password) {
		String pass = passwordEncoder.encode(password);
		return employeeRepository.changePasswordEmployee(id, pass);
	}

	@Override
	public boolean changeStatusEmployee(String id, int status) {
		return employeeRepository.changeStatusEmployee(id, status);
	}

	@Override
	public boolean getStatusById(String id) {
		return employeeRepository.getStatusById(id);
	}

	// override from userdetailservice

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.getEmployeeByPhone(username);
		if (employee == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		grantedAuthorities.add(new SimpleGrantedAuthority(employee.getRole()));

//		return new MyUserDetails(employee);
//		return new org.springframework.security.core.userdetails.User(employee.getPhone(), employee.getPassword(),
//				grantedAuthorities);

		UserDetails user = User.withUsername(employee.getEmail()).password(employee.getPassword())
				.authorities(employee.getRole()).build();

		return user;
	}

	@SuppressWarnings("unused")
	private Collection<GrantedAuthority> getAuthorities(Employee user) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return authorities;
	}
}
