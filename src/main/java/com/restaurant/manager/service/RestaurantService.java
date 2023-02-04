package com.restaurant.manager.service;

import com.restaurant.manager.request.RestaurantRequest;

public interface RestaurantService {
	public String createRestaurant(RestaurantRequest restaurantRequest);

	public String updateRestaurant(int restaurantId, RestaurantRequest restaurantRequest);

	public RestaurantRequest detailRestaurant(int restaurantId);

	public String changeStatusRestaurant(int restaurantId);
}
