package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Food;

public interface FoodService {
	public boolean createFood(Food food);

	public Food detailFood(int id);

	public boolean updateFood(Food food);

	public boolean deleteFood(int id);

	public boolean changeStatusFood(int id, int status);

	public List<Food> getFoodIdByRestaurantIdAndBranchId(String restaurantId, String branchId);
	
	public List<Food> listFood(String restaurantId, String branchId, int status);
}
