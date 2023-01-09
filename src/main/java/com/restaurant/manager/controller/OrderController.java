package com.restaurant.manager.controller;

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

import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	private String success = "success";
	private BaseResponse baseResponse = new BaseResponse();

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createOrder(@RequestBody OrderRequest orderRequest) {
		String message = orderService.createOrder(orderRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(orderRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailOrder(@RequestParam("tableId") int tableId) {
		OrderRequest orderRequest = orderService.detailOrder(tableId);
		if (orderRequest == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(orderRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/pay")
	ResponseEntity<BaseResponse> payOrder(@RequestParam("tableId") int tableId) {
		Float total = orderService.payOrder(tableId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(total.toString());
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateOrder(@RequestBody OrderRequest orderRequest) {
		String message = orderService.updateOrder(orderRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(message);
			baseResponse.setData(orderRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
