package com.restaurant.manager.service;

import com.restaurant.manager.model.Brand;

public interface BrandService {
	public boolean createBrand(Brand brand);

	public Brand detailBrand(String restaurantId);

	public boolean updateBrand(Brand brand);

	public boolean changeStatusBrand(String restaurantId, boolean status);
	
	public boolean getStatusbyRestaurantId(String restaurantId);
}
