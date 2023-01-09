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
	private BaseResponse baseResponse = new BaseResponse();

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createWarehouse(@Valid @RequestParam("materialId") int id,
			@RequestBody WarehouseRequest warehouseRequest) {
		String message = warehouseService.createWarehouse(id, warehouseRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(warehouseRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-warehouse")
	ResponseEntity<BaseResponse> listWarehouse(@RequestParam("employeeId") String employeeId,
			@RequestParam("material code") String materialCode) {
		List<WarehouseRequest> warehouseRequests = warehouseService.detailWarehouse(employeeId, materialCode);
		if (warehouseRequests == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(warehouseRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
