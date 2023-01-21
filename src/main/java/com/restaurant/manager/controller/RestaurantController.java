package com.restaurant.manager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.request.RestaurantRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	RestaurantService restaurantService;
	private String success = "success";

	@PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BaseResponse> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = restaurantService.createRestaurant(restaurantRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(restaurantRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateRestaurant(@Valid @RequestParam(name = "id") String id,
			@Valid @RequestBody RestaurantRequest restaurantRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = restaurantService.updateRestaurant(id, restaurantRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(restaurantRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailRestaurant(@Valid @RequestParam(name = "id") String id) {
		RestaurantRequest restaurantRequest = restaurantService.detailRestaurant(id);
		BaseResponse baseResponse = new BaseResponse();
		if (restaurantRequest == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(restaurantRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changeStatusRestaurant(@Valid @RequestParam(name = "id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		String message = restaurantService.changeStatusRestaurant(id);
		baseResponse.setStatus(200);
		baseResponse.setMessage(message);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
