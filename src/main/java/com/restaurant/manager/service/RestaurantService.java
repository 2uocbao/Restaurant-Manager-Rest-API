package com.restaurant.manager.service;

import com.restaurant.manager.model.Restaurants;

public interface RestaurantService {
	public boolean createRestaurant(Restaurants restaurant);

	public boolean updateRestaurant(Restaurants restaurant);

	public Restaurants detailRestaurant(String id);

	public boolean changeStatusRestaurant(String id, int status);

	public Restaurants getRestaurantbyPhone(String phone);

	public Restaurants getRestaurantbyEmail(String email);

	public int getStatusById(String id);
}
