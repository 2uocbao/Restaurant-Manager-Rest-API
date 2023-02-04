package com.restaurant.manager.repository;

import com.restaurant.manager.model.Restaurant;

public interface RestaurantRepository {
	public boolean createRestaurant(Restaurant restaurant);

	public boolean updateRestaurant(Restaurant restaurant);

	public Restaurant detailRestaurant(int id);

	public boolean changeStatusRestaurant(int id, int status);

	public Restaurant getRestaurantbyPhone(String phone);

	public Restaurant getRestaurantbyEmail(String email);

	public int getStatusById(int id);
}
