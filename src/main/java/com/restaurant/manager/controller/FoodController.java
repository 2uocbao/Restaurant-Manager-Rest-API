package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.request.FoodRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.FoodDetailService;
import com.restaurant.manager.service.FoodService;
import com.restaurant.manager.service.MaterialService;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;

	@Autowired
	FoodDetailService foodDetailService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	BranchService branchService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	MaterialService materialService;

	@PostMapping("/create")
	ResponseEntity<String> createFood(@RequestBody FoodRequest foodRequest) {
		String message = null;
		boolean success = false;
		Employee employee = employeeService.detailEmployee(foodRequest.getEmployeeId());
		if (employee.getStatus() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Không thể thêm món mới, vì bạn không hoạt động");
		}
		Restaurants restaurant = restaurantService.detailRestaurant(employee.getRestaurant().getId());
		Branch branch = employee.getBranch() != null ? branchService.detailBranch(employee.getBranch().getId()) : null;
		String branchId = branch != null ? branch.getId() : "";
		List<Food> listFood = foodService.getFoodIdByRestaurantIdAndBranchId(restaurant.getId(), branchId);
		for (Food food : listFood) {
			if (food.getName().equalsIgnoreCase(foodRequest.getName())) {
				return ResponseEntity.status(HttpStatus.OK).body("Món ăn có tên này đã có");
			}
		}
		Food food = new Food();
		food.setRestaurant(restaurant);
		food.setBranch(branch);
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setStatus(1);
		success = foodService.createFood(food) ? true : false;
		foodDetail fooddetail = new foodDetail();
		for (String materialCode : foodRequest.getMaterialCode()) {
			Material material = null;
			material = materialService.detailMaterial(materialCode, employee.getRestaurant().getId(), branchId);
			fooddetail.setFood(food);
			fooddetail.setMaterialCode(material.getCode());
			foodDetailService.createFoodDetail(fooddetail);
		}
		message = success ? "Thêm món ăn mới thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailFood(@RequestParam("id") int id) {
		Food food = foodService.detailFood(id);
		if (food == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu của món ăn này");
		}
		List<foodDetail> listFoodDetail = foodDetailService.listFoodDetail(food.getId());
		List<String> nameMaterial = new ArrayList<>();
		String branchId = food.getBranch() != null ? food.getBranch().getId() : "";
		for (foodDetail listFooddetail : listFoodDetail) {
			Material material = materialService.detailMaterial(listFooddetail.getMaterialCode(),
					food.getRestaurant().getId(), branchId);
			nameMaterial.add(material.getName());
		}
		FoodRequest foodRequest = new FoodRequest();
		foodRequest.setFoodId(food.getId());
		foodRequest.setName(food.getName());
		foodRequest.setPrice(food.getPrice());
		foodRequest.setType(food.getType());
		foodRequest.setMaterialCode(nameMaterial);
		return ResponseEntity.status(HttpStatus.OK).body(foodRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateFood(@RequestBody FoodRequest foodRequest) {
		String message = null;
		Food food = foodService.detailFood(foodRequest.getFoodId());
		if (food == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu về món ăn này");
		}
		Branch branch = food.getBranch() != null ? branchService.detailBranch(food.getBranch().getId()) : null;
		String branchId = branch != null ? branch.getId() : "";
		List<Food> listFood = foodService.getFoodIdByRestaurantIdAndBranchId(food.getRestaurant().getId(), branchId);
		for (Food food1 : listFood) {
			if (food1.getName().equalsIgnoreCase(foodRequest.getName())
					&& !food.getName().equalsIgnoreCase(food1.getName())) {
				return ResponseEntity.status(HttpStatus.OK).body("Món ăn có tên này đã có");
			}
		}
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		foodService.updateFood(food);
		List<foodDetail> listFoodDetails = foodDetailService.listFoodDetail(foodRequest.getFoodId());
		List<String> materialCode = new ArrayList<>();
		List<String> materialCode2 = new ArrayList<>();
		for (foodDetail fooddetail : listFoodDetails) {
			materialCode.add(fooddetail.getMaterialCode());
			materialCode2.add(fooddetail.getMaterialCode());
		}
		materialCode.removeAll(foodRequest.getMaterialCode());
		foodRequest.getMaterialCode().removeAll(materialCode2);
		for (String materialcode : materialCode) {
			message = foodDetailService.deleteFoodDetailByMateCode(food.getId(), materialcode)
					? "Cập nhật thông tin thành công"
					: "không thành công";
		}
		for (String materialcd : foodRequest.getMaterialCode()) {
			Material material = null;
			foodDetail fooddetail = new foodDetail();
			material = materialService.detailMaterial(materialcd, food.getRestaurant().getId(), branchId);
			fooddetail.setFood(food);
			fooddetail.setMaterialCode(material.getCode());
			message = foodDetailService.createFoodDetail(fooddetail) ? "Cập nhật thông tin thành công"
					: "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list-food")
	ResponseEntity<?> listFood(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Food> listFood = foodService.getFoodIdByRestaurantIdAndBranchId(employee.getRestaurant().getId(),
				branchId);
		List<FoodRequest> listFoodRequest = new ArrayList<>();
		for (Food food : listFood) {
			FoodRequest foodRequest = new FoodRequest();
			List<String> nameMaterial = new ArrayList<>();
			List<foodDetail> listFoodDetail = foodDetailService.listFoodDetail(food.getId());
			for (foodDetail listFooddetail : listFoodDetail) {
				Material material = materialService.detailMaterial(listFooddetail.getMaterialCode(),
						food.getRestaurant().getId(), branchId);
				nameMaterial.add(material.getName());
			}
			foodRequest.setFoodId(food.getId());
			foodRequest.setName(food.getName());
			foodRequest.setPrice(food.getPrice());
			foodRequest.setType(food.getType());
			foodRequest.setMaterialCode(nameMaterial);
			listFoodRequest.add(foodRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listFoodRequest);
	}

	@DeleteMapping("/delete")
	ResponseEntity<String> deleteFood(@RequestParam("id") int id) {
		String message = null;
		Food food = foodService.detailFood(id);
		message = foodService.deleteFood(id) ? "Đã xóa món ăn" : "Không thành công";
		foodDetailService.deleteFoodDetail(food.getId());
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
