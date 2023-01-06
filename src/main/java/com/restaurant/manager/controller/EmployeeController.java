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
	private BaseResponse baseResponse = new BaseResponse();

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
		String message = employeeService.createEmployee(employeeRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(employeeRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailEmployee(@RequestParam("id") String id) {
		EmployeeRequest employeeRequest = employeeService.detailEmployee(id);
		if (employeeRequest != null) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(employeeRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequest,
			@RequestParam("id") String id) {
		String message = employeeService.updateEmployee(id, employeeRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(employeeRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/delete")
	ResponseEntity<BaseResponse> deleteEmployee(@RequestParam("id") String id) {
		String message = employeeService.deleteEmployee(id);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(message);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list")
	ResponseEntity<BaseResponse> listEmployeeFromBranch(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		List<EmployeeRequest> employeeRequests = employeeService.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
		if (employeeRequests == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(employeeRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/change-password")
	ResponseEntity<BaseResponse> changepasswordEmployee(@RequestParam("id") String id,
			@RequestParam("newPassword") String newPassword) {
		String message = employeeService.changePasswordEmployee(id, newPassword);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(message);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changestatusEmployee(@RequestParam("id") String id) {
		String message = employeeService.changeStatusEmployee(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
