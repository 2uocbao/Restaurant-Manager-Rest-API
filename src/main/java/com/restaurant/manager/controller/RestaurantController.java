package com.restaurant.manager.controller;

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

import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.request.RestaurantRequest;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	CheckService checkService;

	@PostMapping("/create")
	ResponseEntity<String> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
		String message;
		Restaurants restaurant = new Restaurants();
		if (!checkService.isValidEmail(restaurantRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.OK).body("Email không hợp lệ");
		} else if (!checkService.checkPhone(restaurantRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không hơp lệ");
		} else if (!checkService.checkName(restaurantRequest.getName())) {
			return ResponseEntity.status(HttpStatus.OK).body("Tên không hợp lệ");
		} else {
			if (restaurantService.getRestaurantbyEmail(restaurantRequest.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.OK).body("Email đã được sử dụng");
			} else if (restaurantService.getRestaurantbyPhone(restaurantRequest.getPhone()) != null) {
				return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại đã được sử dụng");
			} else {
				restaurant.setId(restaurantRequest.getPhone().trim());
				restaurant.setName(restaurantRequest.getName().replaceAll("\\s+", " ").trim());
				restaurant.setEmail(restaurantRequest.getEmail().trim());
				restaurant.setPhone(restaurantRequest.getPhone().trim());
				restaurant.setInfo(restaurantRequest.getInfo().replaceAll("\\s+", " ").trim());
				restaurant.setAddress(restaurantRequest.getAddress().replaceAll("\\s+", " ").trim());
				restaurant.setStatus(1);
				message = restaurantService.createRestaurant(restaurant) ? "Tạo nhà hàng thành công"
						: "Không thành công";
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateRestaurant(@Valid @RequestParam(name = "id") String id,
			@Valid @RequestBody RestaurantRequest restaurantRequest) {
		String message;
		Restaurants restaurant = restaurantService.detailRestaurant(id);
		restaurant.setPhone("");
		restaurant.setEmail("");
		if (!checkService.isValidEmail(restaurantRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.OK).body("Email không hợp lệ");
		} else if (!checkService.checkPhone(restaurantRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không hơp lệ");
		} else if (!checkService.checkName(restaurantRequest.getName())) {
			return ResponseEntity.status(HttpStatus.OK).body("Tên không hợp lệ");
		} else {
			restaurantService.updateRestaurant(restaurant);
			if (restaurantService.getRestaurantbyEmail(restaurantRequest.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.OK).body("Email đã được sử dụng");
			} else if (restaurantService.getRestaurantbyPhone(restaurantRequest.getPhone()) != null) {
				return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại đã được sử dụng");
			}
			restaurant.setName(restaurantRequest.getName().replaceAll("\\s+", " ").trim());
			restaurant.setEmail(restaurantRequest.getEmail().trim());
			restaurant.setPhone(restaurantRequest.getPhone().trim());
			restaurant.setInfo(restaurantRequest.getInfo().replaceAll("\\s\\s+", " ").trim());
			restaurant.setAddress(restaurantRequest.getAddress().replaceAll("\\s\\s+", " ").trim());
			message = restaurantService.updateRestaurant(restaurant) ? "Cập nhật thông tin thành công"
					: "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailRestaurant(@Valid @RequestParam(name = "id") String id) {
		Restaurants restaurant = restaurantService.detailRestaurant(id);
		RestaurantRequest restaurantRequest = new RestaurantRequest();
		if (restaurant == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng không tồn tại");
		} else {
			restaurantRequest.setName(restaurant.getName());
			restaurantRequest.setEmail(restaurant.getEmail());
			restaurantRequest.setPhone(restaurant.getPhone());
			restaurantRequest.setInfo(restaurant.getInfo());
			restaurantRequest.setAddress(restaurant.getAddress());
		}
		return ResponseEntity.status(HttpStatus.OK).body(restaurantRequest);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusRestaurant(@Valid @RequestParam(name = "id") String id) {
		String message;
		int statusNow = restaurantService.getStatusById(id) == 1 ? 0 : 1;
		if (statusNow == 1) {
			message = restaurantService.changeStatusRestaurant(id, statusNow) ? "Nhà hàng đang hoạt động"
					: "Không thành công";
		} else {
			message = restaurantService.changeStatusRestaurant(id, statusNow) ? "Nhà hàng đang tạm ngưng hoạt động"
					: "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
