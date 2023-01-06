package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.EmployeeRequest;

public interface EmployeeService {
	public String createEmployee(EmployeeRequest employeeRequest);

	public EmployeeRequest detailEmployee(String employeeId);

	public String updateEmployee(String employeeId, EmployeeRequest employeeRequest);

	public String deleteEmployee(String employeeId);

	public List<EmployeeRequest> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId);

	public String changePasswordEmployee(String employeeId, String password);

	public String changeStatusEmployee(String employeeId);
}
