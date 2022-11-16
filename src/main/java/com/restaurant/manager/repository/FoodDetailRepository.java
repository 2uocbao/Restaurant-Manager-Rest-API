package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.foodDetail;

public interface FoodDetailRepository {
	public foodDetail detailFood(int foodId, String materialCode);

	public boolean createFoodDetail(foodDetail foodDetail);

	public boolean updateFoodDetail(foodDetail foodDetail);

	public boolean deleteFoodDetail(int foodId);
	
	public boolean deleteFoodDetailByMateCode(int foodId, String materialCode);

	public List<foodDetail> listFoodDetail(int foodId);
}
