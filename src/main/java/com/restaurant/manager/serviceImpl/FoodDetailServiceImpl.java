package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.service.FoodDetailService;

@Service
public class FoodDetailServiceImpl implements FoodDetailService {

	@Autowired
	FoodDetailRepository foodDetailRepository;

	@Override
	public boolean createFoodDetail(foodDetail foodDetail) {
		return foodDetailRepository.createFoodDetail(foodDetail);
	}

	@Override
	public boolean deleteFoodDetail(int id) {
		return foodDetailRepository.deleteFoodDetail(id);
	}

	@Override
	public List<foodDetail> listFoodDetail(int foodId) {
		return foodDetailRepository.listFoodDetail(foodId);
	}

	@Override
	public boolean updateFoodDetail(foodDetail foodDetail) {
		return foodDetailRepository.updateFoodDetail(foodDetail);
	}
}
