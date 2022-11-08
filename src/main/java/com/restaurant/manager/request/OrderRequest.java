package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
	
	@JsonProperty("food id")
	private List<Integer> foodId;

	@JsonProperty("food")
	private List<String> food;

	@JsonProperty("quantity")
	private List<Integer> quantity;

	@JsonProperty("description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getFood() {
		return food;
	}

	public void setFood(List<String> food) {
		this.food = food;
	}

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}

	public List<Integer> getFoodId() {
		return foodId;
	}

	public void setFoodId(List<Integer> foodId) {
		this.foodId = foodId;
	}
}
