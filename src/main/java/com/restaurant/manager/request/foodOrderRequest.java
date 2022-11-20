package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class foodOrderRequest {
	@JsonProperty("food")
	private String food;

	@JsonProperty("quantity")
	private int quantity;
	
	@JsonProperty("price")
	private int price;

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
