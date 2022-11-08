package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.sercurity.hashingwithBCrypt;
import com.restaurant.manager.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	hashingwithBCrypt hashingwithBCrypt;

	@Override
	public boolean createEmployee(Employee employee) {
		String pass = hashingwithBCrypt.encodePassword(employee.getPassword());
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
		return hashingwithBCrypt.matchedPassword(password, passwordDB);
	}

	@Override
	public List<Employee> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId) {
		return employeeRepository.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
	}

	@Override
	public Employee getEmployeeByPhone(String phone) {
		return employeeRepository.getEmployeeByPhone(phone);
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.getEmployeeByEmail(email);
	}

	@Override
	public boolean changePasswordEmployee(String id, String password) {
		String pass = hashingwithBCrypt.encodePassword(password);
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
}
