package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.FoodDetail;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.request.FoodRequest;
import com.restaurant.manager.request.MaterialFood;
import com.restaurant.manager.request.MaterialFoodRequest;
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
	private String nosuccess = "no success";

	@Override
	public String createFood(FoodRequest foodRequest) {
		boolean successful = false;
		List<Food> foods = foodRepository.listFood(foodRequest.getRestaurantId(), foodRequest.getBranchId());
		for (Food food : foods) {
			if (food.getName().equalsIgnoreCase(foodRequest.getName())) {
				return "Món ăn có tên này đã có";
			}
		}
		Food food = new Food();
		food.setRestaurant(restaurantRepository.detailRestaurant(foodRequest.getRestaurantId()));
		food.setBranch(
				foodRequest.getBranchId() == 0 ? null : branchRepository.detailBranch(foodRequest.getBranchId()));
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setImage(foodRequest.getImage());
		food.setStatus(1);
		successful = foodRepository.createFood(food);
		return successful ? success : nosuccess;
	}

	@Override
	public String addMaterial(int foodId, MaterialFood materialFood) {
		boolean successful = false;
		Food food = foodRepository.detailFood(foodId);
		if (food == null) {
			return "Can't find food";
		}
		for (MaterialFoodRequest materialFoods : materialFood.getMaterialFoodRequests()) {
			FoodDetail foodDetail = new FoodDetail();
			Material material = materialRepository.detailMaterial(materialFoods.getMaterial());
			foodDetail.setFood(food);
			foodDetail.setMaterial(material);
			foodDetail.setQuantity(materialFoods.getQuantity());
			successful = foodDetailRepository.createFoodDetail(foodDetail);
		}
		return successful ? success : nosuccess;
	}

	@Override
	public FoodRequest detailFood(int foodId) {
		Food food = foodRepository.detailFood(foodId);
		if (food != null) {
			return foodRequest(food);
		}
		return null;
	}

	@Override
	public String updateFood(int foodId, FoodRequest foodRequest) {
		boolean successful = false;
		Food food = foodRepository.detailFood(foodId);
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food.setType(foodRequest.getType());
		food.setImage(foodRequest.getImage());
		successful = foodRepository.updateFood(food);
		return successful ? success : nosuccess;
	}

	@Override
	public String upMaterial(int foodId, MaterialFood materialFood) {
		boolean successful = false;
		List<FoodDetail> foodDetails = foodDetailRepository.listFoodDetail(foodId);
		for (MaterialFoodRequest materialFoods : materialFood.getMaterialFoodRequests()) {
			for (FoodDetail foodDetail : foodDetails) {
				if (materialFoods.getMaterial() == foodDetail.getMaterial().getId()) {
					foodDetail.setQuantity(materialFoods.getQuantity());
					successful = foodDetailRepository.updateFoodDetail(foodDetail);
				}
			}
		}
		return successful ? success : nosuccess;
	}

	@Override
	public String delMaterial(int foodId, int materialId) {
		return foodDetailRepository.deleteMaterialInFood(foodId, materialId) ? success : "No success";
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
	public List<FoodRequest> listFood(int restaurantId, int branchId) {
		List<FoodRequest> foodRequests = new ArrayList<>();
		List<Food> foods = foodRepository.listFood(restaurantId, branchId);
		for (Food food : foods) {
			FoodRequest foodRequest = new FoodRequest();
			foodRequest.setRestaurantId(food.getRestaurant().getId());
			foodRequest.setBranchId(food.getBranch() != null ? food.getBranch().getId() : 0);
			foodRequest.setFoodId(food.getId());
			foodRequest.setName(food.getName());
			foodRequest.setPrice(food.getPrice());
			foodRequest.setType(food.getType());
			foodRequest.setImage(food.getImage());
			foodRequest.setStatus(food.getStatus());
			foodRequests.add(foodRequest);
		}
		return foodRequests;
	}

	@Override
	public List<FoodRequest> listFoodByStatus(int restaurantId, int branchId, int status) {
		List<FoodRequest> foodRequests = new ArrayList<>();
		List<Food> foods = foodRepository.listFood(restaurantId, branchId);
		for (Food food : foods) {
			if (food.getStatus() == status) {
				foodRequests.add(foodRequest(food));
			}
		}
		return foodRequests;
	}

	@Override
	public List<MaterialFoodRequest> materialFoods(int foodId) {
		List<FoodDetail> foodDetails = foodDetailRepository.listFoodDetail(foodId);
		List<MaterialFoodRequest> materialFoods = new ArrayList<>();
		for (FoodDetail foodDetail : foodDetails) {
			MaterialFoodRequest materialFood = new MaterialFoodRequest();
			materialFood.setMaterial(foodDetail.getMaterial().getId());
			materialFood.setQuantity(foodDetail.getQuantity());
			materialFoods.add(materialFood);
		}
		return materialFoods;
	}

	public FoodRequest foodRequest(Food food) {
		FoodRequest foodRequest = new FoodRequest();
		foodRequest.setRestaurantId(food.getRestaurant().getId());
		foodRequest.setBranchId(food.getBranch() != null ? food.getBranch().getId() : 0);
		foodRequest.setFoodId(food.getId());
		foodRequest.setName(food.getName());
		foodRequest.setPrice(food.getPrice());
		foodRequest.setType(food.getType());
		foodRequest.setImage(food.getImage());
		foodRequest.setStatus(food.getStatus());
		return foodRequest;
	}
}
