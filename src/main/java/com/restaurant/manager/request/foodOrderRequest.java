package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class foodOrderRequest {
	@JsonProperty("food")
	private String food;

	@JsonProperty("quantity")
	private int quantity;
	
	@JsonProperty("price")
	private float price;
	
	@JsonProperty("total")
	private float total;

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	
}
