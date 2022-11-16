package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.foodDetail;

public interface FoodDetailService {
	public foodDetail detailFood(int foodId, String materialCode);
	
	public boolean createFoodDetail(foodDetail foodDetail);

	public boolean deleteFoodDetail(int id);

	public List<foodDetail> listFoodDetail(int foodId);

	public boolean deleteFoodDetailByMateCode(int foodId, String materialCode);

	public boolean updateFoodDetail(foodDetail foodDetail);
}
