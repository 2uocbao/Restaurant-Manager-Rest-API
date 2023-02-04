package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.request.EmployeeRequest;

public interface EmployeeService {
	public String createEmployee(EmployeeRequest employeeRequest);

	public EmployeeRequest detailEmployee(int employeeId);

	public String updateEmployee(int employeeId, EmployeeRequest employeeRequest);

	public String deleteEmployee(int employeeId);

	public List<EmployeeRequest> listEmpoyeeByResIdOrBranId(int restaurantId, int branchId);

	public String changePasswordEmployee(int employeeId, String password);

	public String changeStatusEmployee(int employeeId);

	public Employee getEmployeeByPhone(String phone);

	public String giveRole(int employeeId, int roleId);

	public String removeRole(int employeeId, int roleId);
}
