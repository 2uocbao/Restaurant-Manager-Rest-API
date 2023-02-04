package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodRequest {
	@JsonProperty("restaurantId")
	private int restaurantId;
	
	@JsonProperty("branchId")
	private int branchId;

	@JsonProperty("foodId")
	private int foodId;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("price")
	private int price;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("status")
	private int status;

	
	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
