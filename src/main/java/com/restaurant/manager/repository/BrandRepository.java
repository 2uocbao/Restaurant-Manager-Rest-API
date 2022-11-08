package com.restaurant.manager.repository;

import com.restaurant.manager.model.Brand;

public interface BrandRepository {
	public boolean createBrand(Brand brand);

	public Brand detailBrand(String restaurantId);

	public boolean updateBrand(Brand brand);

	public boolean changeStatusBrand(String restaurantId, boolean status);

	public boolean getStatusbyRestaurantId(String restaurantId);
}
