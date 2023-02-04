package com.restaurant.manager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.TableRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {

	@Autowired
	TableService tableService;
	private String success = "success";

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createTable(@Valid @RequestBody TableRequest tableRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = tableService.createTable(tableRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(tableRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailTable(@Valid @RequestParam(name = "id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		TableRequest tableRequest = tableService.detailTable(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(tableRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list")
	ResponseEntity<BaseResponse> listTable(@Valid @RequestParam(name = "restaurantId") int restaurantId,
			@Valid @RequestParam(name = "branchId") int branchId) {
		BaseResponse baseResponse = new BaseResponse();
		List<TableRequest> tableRequests = tableService.listTableByBranchIdandRestaurantId(restaurantId, branchId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(tableRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateTable(@Valid @RequestParam(name = "id") int id,
			@Valid @RequestBody TableRequest tableRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = tableService.updateTable(id, tableRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(tableRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-by-status")
	ResponseEntity<BaseResponse> listTablebyStatus(@Valid @RequestParam(name = "restaurantId") int restaurantId,
			@Valid @RequestParam(name = "branchId") int branchId, @RequestParam("status") int status) {
		BaseResponse baseResponse = new BaseResponse();
		List<TableRequest> tableRequests = tableService.listTableByStatus(restaurantId, branchId, status);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(tableRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("find-table")
	ResponseEntity<BaseResponse> findTablebyKeySearch(@Valid @RequestParam(name = "restaurantId") int restaurantId,
			@Valid @RequestParam(name = "branchId") int branchId,
			@Valid @RequestParam(name = "keySearch") String keySearch) {
		BaseResponse baseResponse = new BaseResponse();
		List<TableRequest> tableRequests = tableService.findTable(restaurantId, branchId, keySearch);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(tableRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
