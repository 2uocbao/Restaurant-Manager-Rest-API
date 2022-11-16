package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.restaurant.manager.request.materialFood;
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
		String message;
		Restaurants restaurant = restaurantService.detailRestaurant(foodRequest.getRestaurantId());
		if (restaurant == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có thông tin về nhà hàng này");
		}
		Branch branch = foodRequest.getBranchId() != null ? branchService.detailBranch(foodRequest.getBranchId())
				: null;
		String branchId = branch != null ? branch.getId() : "";
		if (!branchId.equals("")) {
			branch = branchService.detailBranch(branchId);
			if (branch == null || !branch.getRestaurant().getId().equals(restaurant.getId())) {
				return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh không tồn tại");
			}
		}
		List<Food> listFood = foodService.getFoodIdByRestaurantIdAndBranchId(restaurant.getId(), branchId);
		for (Food food : listFood) {
			if (food.getName().equalsIgnoreCase(foodRequest.getName())) {
				return ResponseEntity.status(HttpStatus.OK).body("Món ăn có tên này đã có");
			}
		}
		for (materialFood materialCode : foodRequest.getMaterialCode()) {
			Material material = materialService.detailMaterial(materialCode.getMaterial(), restaurant.getId(),
					branchId);
			if (material == null) {
				return ResponseEntity.status(HttpStatus.OK).body("Nguyên liệu này chưa có");
			}
		}
		Food food = new Food();
		food.setRestaurant(restaurant);
		food.setBranch(branch);
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setStatus(1);
		message = foodService.createFood(food) ? "Thêm món ăn mới thành công" : "";
		foodDetail fooddetail = new foodDetail();
		for (materialFood materialCode : foodRequest.getMaterialCode()) {
			Material material = null;
			material = materialService.detailMaterial(materialCode.getMaterial(), restaurant.getId(), branchId);
			fooddetail.setFood(food);
			fooddetail.setMaterialCode(material.getCode());
			fooddetail.setQuantity(materialCode.getQuantity());
			foodDetailService.createFoodDetail(fooddetail);
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<Object> detailFood(@RequestParam("id") int id) {
		Food food = foodService.detailFood(id);
		if (food == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu của món ăn này");
		}
		List<foodDetail> listFoodDetail = foodDetailService.listFoodDetail(food.getId());
		List<materialFood> nameMaterial = new ArrayList<>();
		String branchId = food.getBranch() != null ? food.getBranch().getId() : "";
		for (foodDetail listFooddetail : listFoodDetail) {
			materialFood materiaL = new materialFood();
			Material material = materialService.detailMaterial(listFooddetail.getMaterialCode(),
					food.getRestaurant().getId(), branchId);
			materiaL.setMaterial(material.getName());
			materiaL.setQuantity(listFooddetail.getQuantity());
			nameMaterial.add(materiaL);
		}
		FoodRequest foodRequest = new FoodRequest();
		foodRequest.setRestaurantId(food.getRestaurant().getId());
		foodRequest.setBranchId(branchId);
		foodRequest.setFoodId(food.getId());
		foodRequest.setName(food.getName());
		foodRequest.setPrice(food.getPrice());
		foodRequest.setType(food.getType());
		foodRequest.setStatus(food.getStatus());
		foodRequest.setMaterialCode(nameMaterial);
		return ResponseEntity.status(HttpStatus.OK).body(foodRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateFood(@RequestBody FoodRequest foodRequest) {
		String message;
		Food food = foodService.detailFood(foodRequest.getFoodId());
		if (food == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu về món ăn này");
		}
		Branch branch = food.getBranch() != null ? branchService.detailBranch(food.getBranch().getId()) : null;
		String branchId = branch != null ? branch.getId() : "";
		for (materialFood materialCode : foodRequest.getMaterialCode()) {
			if (materialService.detailMaterial(materialCode.getMaterial(), food.getRestaurant().getId(),
					branchId) == null) {
				return ResponseEntity.status(HttpStatus.OK).body("Nguyên liệu mã " + materialCode + " chưa có");
			}
		}
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
		message = foodService.updateFood(food) ? "Cập nhật thông tin thành công" : "";
		List<foodDetail> listFoodDetails = foodDetailService.listFoodDetail(foodRequest.getFoodId());
		List<String> materialCode = new ArrayList<>();
		List<String> materialCode2 = new ArrayList<>();
		List<String> materialCodeReq = new ArrayList<>();
		HashMap<String, Float> listReq = new HashMap<>();
		for (foodDetail fooddetail : listFoodDetails) {
			materialCode.add(fooddetail.getMaterialCode());
			materialCode2.add(fooddetail.getMaterialCode());
		}
		for (materialFood materialFood : foodRequest.getMaterialCode()) {
			materialCodeReq.add(materialFood.getMaterial());
			listReq.put(materialFood.getMaterial(), materialFood.getQuantity());
		}
		materialCode.removeAll(materialCodeReq);
		materialCodeReq.removeAll(materialCode2);

		for (String materalC : materialCode) {
			materialCode2.remove(materalC);
		}

		for (String materialcode : materialCode) {
			message = foodDetailService.deleteFoodDetailByMateCode(food.getId(), materialcode)
					? "Đã loại bỏ nguyên liệu" + materialcode
					: "không thành công";
		}
		for (String materialcd : materialCodeReq) {
			foodDetail fooddetail = new foodDetail();
			Material material = materialService.detailMaterial(materialcd, food.getRestaurant().getId(), branchId);
			fooddetail.setFood(food);
			fooddetail.setMaterialCode(material.getCode());
			fooddetail.setQuantity(listReq.get(materialcd));
			message = foodDetailService.createFoodDetail(fooddetail) ? "Đã thêm nguyên liệu" + materialcd : "";
		}
		for (String materialcode2 : materialCode2) {
			foodDetail fooddetail = foodDetailService.detailFood(food.getId(), materialcode2);
			fooddetail.setMaterialCode(materialcode2);
			fooddetail.setQuantity(listReq.get(materialcode2));
			message = foodDetailService.updateFoodDetail(fooddetail) ? "Cập nhật thông tin thành công"
					: "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list-food")
	ResponseEntity<Object> listFood(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Food> listFood = foodService.getFoodIdByRestaurantIdAndBranchId(employee.getRestaurant().getId(),
				branchId);
		List<FoodRequest> listFoodRequest = new ArrayList<>();
		for (Food food : listFood) {
			FoodRequest foodRequest = new FoodRequest();
			List<foodDetail> listFoodDetail = foodDetailService.listFoodDetail(food.getId());
			List<materialFood> listmMaterialFoods = new ArrayList<>();
			for (foodDetail listFooddetail : listFoodDetail) {
				Material material = materialService.detailMaterial(listFooddetail.getMaterialCode(),
						food.getRestaurant().getId(), branchId);
				materialFood materialFood = new materialFood();
				materialFood.setMaterial(material.getName());
				materialFood.setQuantity(listFooddetail.getQuantity());
				listmMaterialFoods.add(materialFood);
			}
			foodRequest.setRestaurantId(food.getRestaurant().getId());
			foodRequest.setBranchId(branchId);
			foodRequest.setFoodId(food.getId());
			foodRequest.setName(food.getName());
			foodRequest.setPrice(food.getPrice());
			foodRequest.setType(food.getType());
			foodRequest.setStatus(food.getStatus());
			foodRequest.setMaterialCode(listmMaterialFoods);
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

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusFood(@RequestParam("id") int id) {
		String message;
		Food food = foodService.detailFood(id);
		if (food == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu về món ăn này");
		}
		int status = food.getStatus() == 1 ? 0 : 1;
		if (status == 1) {
			message = foodService.changeStatusFood(id, status) ? "Món này đã hết" : "";
		} else {
			message = foodService.changeStatusFood(id, status) ? "Món này vẫn còn" : "";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
