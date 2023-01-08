package com.restaurant.manager.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.request.FoodRequest;
import com.restaurant.manager.request.materialFood;
import com.restaurant.manager.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepository foodRepository;
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	FoodDetailRepository foodDetailRepository;
	private String success = "success";

	@Override
	public String createFood(FoodRequest foodRequest) {
		boolean successful = false;
		List<Food> foods = foodRepository.getFoodIdByRestaurantIdAndBranchId(foodRequest.getRestaurantId(),
				foodRequest.getBranchId() == null ? "" : foodRequest.getBranchId());
		for (Food food : foods) {
			if (food.getName().equalsIgnoreCase(foodRequest.getName())) {
				return "Món ăn có tên này đã có";
			}
		}
		Food food = new Food();
		food.setRestaurant(restaurantRepository.detailRestaurant(foodRequest.getRestaurantId()));
		food.setBranch(
				foodRequest.getBranchId() == null ? null : branchRepository.detailBranch(foodRequest.getBranchId()));
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setImage(foodRequest.getImage());
		food.setStatus(1);
		successful = foodRepository.createFood(food);
		if (successful) {
			foodDetail fooddetail = new foodDetail();
			for (materialFood materialCode : foodRequest.getMaterialCode()) {
				Material material = materialRepository.detailMaterial(Integer.parseInt(materialCode.getMaterial()));
				fooddetail.setFood(food);
				fooddetail.setMaterialCode(material.getCode());
				fooddetail.setQuantity(materialCode.getQuantity());
				successful = foodDetailRepository.createFoodDetail(fooddetail);
			}
		}
		return successful ? success : "No success";
	}

	@Override
	public FoodRequest detailFood(int foodId) {
		Food food = foodRepository.detailFood(foodId);
		FoodRequest foodRequest = new FoodRequest();
		if (food != null) {
			foodRequest = foodRequest(food);
		}
		return foodRequest;
	}

	@Override
	public String updateFood(int foodId, FoodRequest foodRequest) {
		boolean successful = false;
		Food food = foodRepository.detailFood(foodId);
		List<Food> foods = foodRepository.getFoodIdByRestaurantIdAndBranchId(food.getRestaurant().getId(),
				food.getBranch() == null ? "" : food.getBranch().getId());
		for (Food food1 : foods) {
			if (food1.getName().equalsIgnoreCase(foodRequest.getName())
					&& !food.getName().equalsIgnoreCase(food1.getName())) {
				return "Món ăn có tên này đã có";
			}
		}
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setImage(foodRequest.getImage());
		successful = foodRepository.updateFood(food);
		if (successful) {
			List<String> materialCode = new ArrayList<>();
			Set<String> materialCF = new HashSet<>();
			List<foodDetail> foodDetails = foodDetailRepository.listFoodDetail(foodId);
			for (materialFood materialFood : foodRequest.getMaterialCode()) {
				materialCode.add(materialFood.getMaterial());
				for (foodDetail foodDetail : foodDetails) {
					materialCF.add(foodDetail.getMaterialCode());
					if (materialFood.getMaterial() == foodDetail.getMaterialCode()) {
						foodDetail.setQuantity(materialFood.getQuantity());
						successful = foodDetailRepository.updateFoodDetail(foodDetail);
					}
				}
			}
			materialCode.removeAll(materialCF);
			for (String string : materialCode) {
				for (materialFood materialFood : foodRequest.getMaterialCode()) {
					if (string.equals(materialFood.getMaterial())) {
						foodDetail fooddetail = new foodDetail();
						fooddetail.setFood(food);
						fooddetail.setMaterialCode(string);
						fooddetail.setQuantity(materialFood.getQuantity());
						successful = foodDetailRepository.createFoodDetail(fooddetail);
					}
				}
			}
		}
		return successful ? success : "No success";
	}

	@Override
	public String deleteFood(int id, String materialCode) {
		return foodDetailRepository.deleteFoodDetailByMateCode(id, materialCode) ? success : "No success";
	}

	@Override
	public String changeStatusFood(int foodId) {
		Food food = foodRepository.detailFood(foodId);
		if (food.getStatus() == 0) {
			foodRepository.changeStatusFood(foodId, 1);
			return "Active";
		} else {
			foodRepository.changeStatusFood(foodId, 0);
			return "Inactive";
		}
	}

	@Override
	public List<FoodRequest> getFoodAll(String restaurantId, String branchId) {
		List<FoodRequest> foodRequests = new ArrayList<>();
		List<Food> foods = foodRepository.getFoodIdByRestaurantIdAndBranchId(restaurantId,
				branchId == null ? "" : branchId);
		for (Food food : foods) {
			FoodRequest foodRequest = foodRequest(food);
			foodRequests.add(foodRequest);
		}
		return foodRequests;
	}

	@Override
	public List<FoodRequest> listFoodByStatus(String restaurantId, String branchId, int status) {
		List<FoodRequest> foodRequests = new ArrayList<>();
		List<Food> foods = foodRepository.getFoodIdByRestaurantIdAndBranchId(restaurantId,
				branchId == null ? "" : branchId);
		for (Food food : foods) {
			if (food.getStatus() == 1) {
				FoodRequest foodRequest = foodRequest(food);
				foodRequests.add(foodRequest);
			}
		}
		return foodRequests;
	}

	public FoodRequest foodRequest(Food food) {
		List<foodDetail> listFoodDetail = foodDetailRepository.listFoodDetail(food.getId());
		List<materialFood> nameMaterial = new ArrayList<>();
		String branchId = food.getBranch() != null ? food.getBranch().getId() : "";
		for (foodDetail listFooddetail : listFoodDetail) {
			materialFood materiaL = new materialFood();
			Material material = materialRepository.detailMaterial(Integer.parseInt(materiaL.getMaterial()));
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
		foodRequest.setMaterialCode(nameMaterial);
		foodRequest.setImage(food.getImage());
		foodRequest.setStatus(food.getStatus());
		return foodRequest;
	}
}
