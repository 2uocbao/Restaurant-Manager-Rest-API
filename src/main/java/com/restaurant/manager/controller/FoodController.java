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

import com.restaurant.manager.request.FoodRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;

	private String success = "success";
	private BaseResponse baseResponse = new BaseResponse();

	@PostMapping("/create")
	ResponseEntity<BaseResponse> createFood(@RequestBody FoodRequest foodRequest) {
		String message = foodService.createFood(foodRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(foodRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailFood(@RequestParam("id") int id) {
		FoodRequest foodRequest = foodService.detailFood(id);
		if (foodRequest == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(foodRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateFood(@Valid @RequestParam(name = "id") int id,
			@RequestBody FoodRequest foodRequest) {
		String message = foodService.updateFood(id, foodRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(foodRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-food")
	ResponseEntity<BaseResponse> listFood(@RequestParam("restaurantId") String restaurantId,
			@RequestParam("branchId") String branchId) {
		List<FoodRequest> foodRequests = foodService.getFoodAll(restaurantId, branchId);
		if (foodRequests == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(foodRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/delete-material-food")
	ResponseEntity<BaseResponse> deleteFood(@RequestParam("id") int id,
			@RequestParam("materialCode") String materialCode) {
		String message = foodService.deleteFood(id, materialCode);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changeStatusFood(@RequestParam("id") int id) {
		String message = foodService.changeStatusFood(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-food-status")
	ResponseEntity<BaseResponse> listFoodbyStatus(@RequestParam("restaurantId") String restaurantId,
			@RequestParam("branchId") String branchId, @RequestParam("status") int status) {
		List<FoodRequest> foodRequests = foodService.listFoodByStatus(restaurantId, branchId, status);
		if (foodRequests == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(foodRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
