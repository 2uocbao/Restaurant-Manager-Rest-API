package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Food;

public interface FoodRepository {
	public boolean createFood(Food food);

	public Food detailFood(int id);

	public boolean updateFood(Food food);

	public boolean deleteFood(int id);

	public boolean changeStatusFood(int id, int status);

	public List<Food> listFood(int restaurantId, int branchId);
}
