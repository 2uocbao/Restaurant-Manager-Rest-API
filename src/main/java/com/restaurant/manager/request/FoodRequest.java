package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodRequest {
	@JsonProperty("restaurantId")
	private String restaurantId;
	
	@JsonProperty("branchId")
	private String branchId;

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

	@JsonProperty("material")
	private List<materialFood> materialCode;
	
	@JsonProperty("status")
	private int status;

	
	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
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

	public List<materialFood> getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(List<materialFood> materialCode) {
		this.materialCode = materialCode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
