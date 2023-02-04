package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodOrder {
	@JsonProperty("FoodOrder")
	List<FoodOrderRequest> foodOrderRequests;

	public List<FoodOrderRequest> getFoodOrderRequests() {
		return foodOrderRequests;
	}

	public void setFoodOrderRequests(List<FoodOrderRequest> foodOrderRequests) {
		this.foodOrderRequests = foodOrderRequests;
	}
}
