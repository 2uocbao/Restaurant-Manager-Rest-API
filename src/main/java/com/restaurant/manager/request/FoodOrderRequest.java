package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodOrderRequest {
	@JsonProperty("foodId")
	private int foodId;

	@JsonProperty("quantity")
	private int quantity;

	@JsonProperty("price")
	private float price;

	@JsonProperty("total")
	private float total;

	@JsonProperty("status")
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
}
