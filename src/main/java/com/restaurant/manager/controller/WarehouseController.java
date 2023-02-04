package com.restaurant.manager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.WarehouseRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

	@Autowired
	WarehouseService warehouseService;
	private String success = "success";

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createWarehouse(@Valid @RequestParam("materialId") int id,
			@RequestBody WarehouseRequest warehouseRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = warehouseService.createWarehouse(id, warehouseRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(warehouseRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-warehouse")
	ResponseEntity<BaseResponse> listWarehouse(@Valid @RequestParam("employeeId") int employeeId,
			@Valid @RequestParam("materialId") int materialId) {
		BaseResponse baseResponse = new BaseResponse();
		List<WarehouseRequest> warehouseRequests = warehouseService.listWarehouse(employeeId, materialId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(warehouseRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
