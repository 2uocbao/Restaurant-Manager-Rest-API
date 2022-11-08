package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Food;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepository foodRepository;

	@Override
	public boolean createFood(Food food) {
		return foodRepository.createFood(food);
	}

	@Override
	public Food detailFood(int id) {
		return foodRepository.detailFood(id);
	}

	@Override
	public boolean updateFood(Food food) {
		return foodRepository.updateFood(food);
	}

	@Override
	public boolean deleteFood(int id) {
		return foodRepository.deleteFood(id);
	}

	@Override
	public boolean changeStatusFood(int id, int status) {
		return foodRepository.changeStatusFood(id, status);
	}

	@Override
	public List<Food> getFoodIdByRestaurantIdAndBranchId(String restaurantId, String branchId) {
		return foodRepository.getFoodIdByRestaurantIdAndBranchId(restaurantId, branchId);
	}

	@Override
	public List<Food> listFood(String restaurantId, String branchId, int status) {
		return foodRepository.listFood(restaurantId, branchId, status);
	}
}
