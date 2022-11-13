package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodRequest {
	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("foodId")
	private int foodId;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("price")
	private int price;

	@JsonProperty("type")
	private String type;

	@JsonProperty("materialCode")
	private List<String> materialCode;
	
	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public List<String> getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(List<String> materialCode) {
		this.materialCode = materialCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
