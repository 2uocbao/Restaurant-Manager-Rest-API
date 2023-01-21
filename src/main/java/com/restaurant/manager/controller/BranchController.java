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

import com.restaurant.manager.request.BranchRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.BranchService;

@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	BranchService branchService;

	private String success = "success";

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createBranch(@Valid @RequestBody BranchRequest branchRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = branchService.createBranch(branchRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(branchRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailBranch(@Valid @RequestParam(name = "id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		BranchRequest branchRequest = branchService.detailBranch(id);
		if (branchRequest == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(branchRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateBranch(@Valid @RequestParam(name = "id") String id,
			@Valid @RequestBody BranchRequest branchRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = branchService.updateBranch(id, branchRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(branchRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changeStatusBranch(@Valid @RequestParam(name = "id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = branchService.changeStatusBranch(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-branch")
	ResponseEntity<BaseResponse> listBranchByRestaurantId(@RequestParam("restaurantId") String restaurantId) {
		BaseResponse baseResponse = new BaseResponse();
		List<BranchRequest> branchRequests = branchService.listBranchByRestaurantId(restaurantId);
		if (branchRequests.isEmpty()) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(branchRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}