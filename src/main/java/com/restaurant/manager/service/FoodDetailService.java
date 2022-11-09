package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.foodDetail;

public interface FoodDetailService {
	public boolean createFoodDetail(foodDetail foodDetail);

	public boolean deleteFoodDetail(int id);

	public List<foodDetail> listFoodDetail(int foodId);

	public boolean deleteFoodDetailByMateCode(String materialCode);

	public boolean updateFoodDetail(foodDetail foodDetail);
}
