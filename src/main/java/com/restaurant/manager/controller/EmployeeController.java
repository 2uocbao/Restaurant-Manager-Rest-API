package com.restaurant.manager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.EmployeeRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	private String success = "success";

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.createEmployee(employeeRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(employeeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailEmployee(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		EmployeeRequest employeeRequest = employeeService.detailEmployee(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(employeeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequest,
			@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.updateEmployee(id, employeeRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(employeeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/delete")
	ResponseEntity<BaseResponse> deleteEmployee(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.deleteEmployee(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list")
	ResponseEntity<BaseResponse> listEmployeeFromBranch(@RequestParam("restaurantId") int restaurantId,
			@RequestParam("branchId") int branchId) {
		BaseResponse baseResponse = new BaseResponse();
		List<EmployeeRequest> employeeRequests = employeeService.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(employeeRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/change-password")
	ResponseEntity<BaseResponse> changepasswordEmployee(@RequestParam("id") int id,
			@RequestParam("newPassword") String newPassword) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.changePasswordEmployee(id, newPassword);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changestatusEmployee(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.changeStatusEmployee(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/giveRole")
	ResponseEntity<BaseResponse> giveRole(@RequestParam("employeeId") int employeeId,
			@RequestParam("roleId") int roleId) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.giveRole(employeeId, roleId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/removeRole")
	ResponseEntity<BaseResponse> removeRole(@RequestParam("employeeId") int employeeId,
			@RequestParam("roleId") int roleId) {
		BaseResponse baseResponse = new BaseResponse();
		String message = employeeService.removeRole(employeeId, roleId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
