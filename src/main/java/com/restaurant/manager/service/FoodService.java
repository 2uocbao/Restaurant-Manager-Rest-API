package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.FoodRequest;

public interface FoodService {
	public String createFood(FoodRequest foodRequest);

	public FoodRequest detailFood(int foodId);

	public String updateFood(int foodId, FoodRequest foodRequest);

	public String deleteFood(int id, String materialCode);

	public String changeStatusFood(int foodId);

	public List<FoodRequest> getFoodAll(String restaurantId, String branchId);

	public List<FoodRequest> listFoodByStatus(String restaurantId, String branchId, int status);
}
