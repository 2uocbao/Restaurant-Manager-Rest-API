package com.restaurant.manager.controller;

import java.util.List;

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

import com.restaurant.manager.request.FoodOrder;
import com.restaurant.manager.request.FoodOrderRequest;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	private String success = "success";
	
	@PostMapping("/create")
	ResponseEntity<BaseResponse> createOrder(@RequestBody OrderRequest orderRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = orderService.createOrder(orderRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(orderRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailOrder(@RequestParam("tableId") int tableId) {
		BaseResponse baseResponse = new BaseResponse();
		OrderRequest orderRequest = orderService.detailOrder(tableId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(orderRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/pay")
	ResponseEntity<BaseResponse> payOrder(@RequestParam("orderId") int orderId) {
		BaseResponse baseResponse = new BaseResponse();
		Float total = orderService.payOrder(orderId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(total.toString());
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateOrder(@RequestBody OrderRequest orderRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = orderService.updateOrder(orderRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(orderRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-order")
	ResponseEntity<BaseResponse> listOrder(@RequestParam("employeeId") int employeeId,
			@RequestParam("status") int status) {
		BaseResponse baseResponse = new BaseResponse();
		List<OrderRequest> orderRequests = orderService.listOrder(employeeId, status);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(orderRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/addFood")
	ResponseEntity<BaseResponse> addFood(@RequestParam("orderId") int orderId, @RequestBody FoodOrder foodOrder) {
		BaseResponse baseResponse = new BaseResponse();
		String message = orderService.addFood(orderId, foodOrder);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(foodOrder);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/upFood")
	ResponseEntity<BaseResponse> upFood(@RequestParam("orderId") int orderId, @RequestBody FoodOrder foodOrder) {
		BaseResponse baseResponse = new BaseResponse();
		String message = orderService.upFood(orderId, foodOrder);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(foodOrder);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/listFood")
	ResponseEntity<BaseResponse> listFood(@RequestParam("orderId") int orderId) {
		BaseResponse baseResponse = new BaseResponse();
		List<FoodOrderRequest> foodOrderRequests = orderService.listFoodOrders(orderId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(foodOrderRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/changeStatusFood")
	ResponseEntity<BaseResponse> changeStatusFood(@RequestParam("orderId") int orderId,
			@RequestParam("foodId") int foodId, @RequestParam("status") int status) {
		BaseResponse baseResponse = new BaseResponse();
		String message = orderService.changeStatusFoodOrder(orderId, foodId, status);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
