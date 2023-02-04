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
import com.restaurant.manager.request.MaterialFood;
import com.restaurant.manager.request.MaterialFoodRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;

	private String success = "success";
	
	@PostMapping("/create")
	ResponseEntity<BaseResponse> createFood(@RequestBody FoodRequest foodRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.createFood(foodRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(foodRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/addMaterial")
	ResponseEntity<BaseResponse> addMaterial(@RequestParam("foodId") int foodId,
			@RequestBody MaterialFood materialFood) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.addMaterial(foodId, materialFood);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(materialFood);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/upMaterial")
	ResponseEntity<BaseResponse> upMaterial(@RequestParam("id") int id, @RequestBody MaterialFood materialFood) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.upMaterial(id, materialFood);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(materialFood);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/listMaterial")
	ResponseEntity<BaseResponse> listMaterial(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		List<MaterialFoodRequest> materialFoodRequests = foodService.materialFoods(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(materialFoodRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailFood(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		FoodRequest foodRequest = foodService.detailFood(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(foodRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateFood(@Valid @RequestParam(name = "id") int id,
			@RequestBody FoodRequest foodRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.updateFood(id, foodRequest);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		baseResponse.setData(foodRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-food")
	ResponseEntity<BaseResponse> listFood(@RequestParam("restaurantId") int restaurantId,
			@RequestParam("branchId") int branchId) {
		BaseResponse baseResponse = new BaseResponse();
		List<FoodRequest> foodRequests = foodService.listFood(restaurantId, branchId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(foodRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/removal-material")
	ResponseEntity<BaseResponse> deleteFood(@RequestParam("id") int id, @RequestParam("materialId") int materialId) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.delMaterial(id, materialId);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changeStatusFood(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = foodService.changeStatusFood(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-food-status")
	ResponseEntity<BaseResponse> listFoodbyStatus(@RequestParam("restaurantId") int restaurantId,
			@RequestParam("branchId") int branchId, @RequestParam("status") int status) {
		BaseResponse baseResponse = new BaseResponse();
		List<FoodRequest> foodRequests = foodService.listFoodByStatus(restaurantId, branchId, status);
		baseResponse.setStatus(200);
		baseResponse.setMessage(success);
		baseResponse.setData(foodRequests);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
