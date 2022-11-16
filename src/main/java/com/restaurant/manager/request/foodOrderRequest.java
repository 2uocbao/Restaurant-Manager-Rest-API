package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class foodOrderRequest {
	@JsonProperty("food")
	private String food;

	@JsonProperty("quantity")
	private float quantity;

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

}
