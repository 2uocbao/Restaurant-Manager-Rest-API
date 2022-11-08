package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Employee;

public interface EmployeeRepository {

	public boolean createEmployee(Employee employee);

	public Employee detailEmployee(String id);

	public boolean updateEmployee(Employee employee);

	public boolean deleteEmployee(String id);

	public boolean loginEmployee(String phone, String password);

	public List<Employee> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId);

	public Employee getEmployeeByPhone(String phone);

	public Employee getEmployeeByEmail(String email);

	public String getPasswordByPhone(String phone);

	public boolean changePasswordEmployee(String id, String password);

	public boolean changeStatusEmployee(String id, int status);

	public boolean getStatusById(String id);

	public boolean changeStatusEmployeeByRestaurantId(String restaurantId, int status);

	public boolean changeStatusEmployeeByBranchId(String branchId, int status);
}
