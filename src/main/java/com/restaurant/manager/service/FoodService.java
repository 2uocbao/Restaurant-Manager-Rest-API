package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.FoodRequest;
import com.restaurant.manager.request.MaterialFood;
import com.restaurant.manager.request.MaterialFoodRequest;

public interface FoodService {
	public String createFood(FoodRequest foodRequest);

	public FoodRequest detailFood(int foodId);

	public String updateFood(int foodId, FoodRequest foodRequest);

	public String changeStatusFood(int foodId);

	public List<FoodRequest> listFood(int restaurantId, int branchId);

	public List<FoodRequest> listFoodByStatus(int restaurantId, int branchId, int status);

	public String addMaterial(int foodId, MaterialFood materialFood);

	public String upMaterial(int foodId, MaterialFood materialFood);

	public String delMaterial(int foodId, int materialId);

	public List<MaterialFoodRequest> materialFoods(int foodId);
}
