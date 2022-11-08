package com.restaurant.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Brand;
import com.restaurant.manager.repository.BrandRepository;
import com.restaurant.manager.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandRepository brandRepository;

	@Override
	public boolean createBrand(Brand brand) {
		return brandRepository.createBrand(brand);
	}

	@Override
	public Brand detailBrand(String restaurantId) {
		return brandRepository.detailBrand(restaurantId);
	}

	@Override
	public boolean updateBrand(Brand brand) {
		return brandRepository.updateBrand(brand);
	}

	@Override
	public boolean changeStatusBrand(String restaurantId, boolean status) {
		return brandRepository.changeStatusBrand(restaurantId, status);
	}

	@Override
	public boolean getStatusbyRestaurantId(String restaurantId) {
		return brandRepository.getStatusbyRestaurantId(restaurantId);
	}
}
