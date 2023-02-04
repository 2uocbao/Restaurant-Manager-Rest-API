package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.FoodDetail;

public interface FoodDetailRepository {
	public FoodDetail detailFood(int foodId, int materialId);

	public boolean createFoodDetail(FoodDetail foodDetail);

	public boolean updateFoodDetail(FoodDetail foodDetail);

	public boolean deleteFoodDetail(int foodId);
	
	public boolean deleteMaterialInFood(int foodId, int materialId);

	public List<FoodDetail> listFoodDetail(int foodId);
}
