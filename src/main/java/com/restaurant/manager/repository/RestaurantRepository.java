package com.restaurant.manager.repository;

import com.restaurant.manager.model.Restaurants;

public interface RestaurantRepository {
	public boolean createRestaurant(Restaurants restaurant);

	public boolean updateRestaurant(Restaurants restaurant);

	public Restaurants detailRestaurant(String id);

	public boolean changeStatusRestaurant(String id, int status);

	public Restaurants getRestaurantbyPhone(String phone);

	public Restaurants getRestaurantbyEmail(String email);

	public int getStatusById(String id);
}
