package com.restaurant.manager.repository;

import java.util.List;
import java.util.Optional;

import com.restaurant.manager.model.Employee;

public interface EmployeeRepository{

	public boolean createEmployee(Employee employee);

	public Employee detailEmployee(int id);

	public boolean updateEmployee(Employee employee);

	public boolean deleteEmployee(int id);

	public boolean loginEmployee(String phone, String password);

	public List<Employee> listEmpoyeeByResIdOrBranId(int restaurantId, int branchId);

	public Employee getEmployeeByPhone(String phone);

	public Employee getEmployeeByEmail(String email);

	public String getPasswordByPhone(String phone);

	public boolean changePasswordEmployee(int id, String password);

	public boolean changeStatusEmployee(int id, int status);

	public boolean getStatusById(int id);

	public boolean changeStatusEmployeeByRestaurantId(int restaurantId, int status);

	public boolean changeStatusEmployeeByBranchId(int branchId, int status);

	public Optional<Employee> findByPhone(String phone);
	
	public boolean removeRole(int id, int roleId);
}
