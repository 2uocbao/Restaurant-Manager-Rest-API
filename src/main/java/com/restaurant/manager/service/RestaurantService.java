package com.restaurant.manager.service;

import com.restaurant.manager.request.RestaurantRequest;

public interface RestaurantService {
	public String createRestaurant(RestaurantRequest restaurantRequest);

	public String updateRestaurant(String restaurantId, RestaurantRequest restaurantRequest);

	public RestaurantRequest detailRestaurant(String restaurantId);

	public String changeStatusRestaurant(String restaurantId);
}
