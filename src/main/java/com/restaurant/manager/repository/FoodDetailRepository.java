package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.foodDetail;

public interface FoodDetailRepository {

	public boolean createFoodDetail(foodDetail foodDetail);

	public boolean updateFoodDetail(foodDetail foodDetail);

	public boolean deleteFoodDetail(int foodId);

	public List<foodDetail> listFoodDetail(int foodId);
}
