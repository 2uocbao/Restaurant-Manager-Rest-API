package com.restaurant.manager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.ReportRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@GetMapping("/revenue")
	ResponseEntity<BaseResponse> revenue(@Valid @RequestParam("employeeId") int employeeId,
			@Valid @RequestParam("fromDate") String fromDate) {
		BaseResponse baseResponse = new BaseResponse();
		ReportRequest reportRequest = reportService.revenue(employeeId, fromDate, null);
		baseResponse.setStatus(200);
		baseResponse.setMessage("success");
		baseResponse.setData(reportRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@RequestMapping("/food")
	ResponseEntity<BaseResponse> food(@Valid @RequestParam("employeeId") int employeeId,
			@Valid @RequestParam("foodId") int foodId, @Valid @RequestParam("fromDate") String fromDate) {
		BaseResponse baseResponse = new BaseResponse();
		ReportRequest reportRequest = reportService.food(employeeId, foodId, fromDate, fromDate);
		baseResponse.setStatus(200);
		baseResponse.setMessage("success");
		baseResponse.setData(reportRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
