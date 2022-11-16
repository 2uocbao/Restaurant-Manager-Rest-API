package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialRequest {
	@JsonProperty("restaurantId")
	private String restaurantId;
	
	@JsonProperty("branchId")
	private String branchId;
	
	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cost")
	private int cost;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("quantity")
	private float quantity;
	
	@JsonProperty("stockEnd")
	private float stockEnd;

	@JsonProperty("whereProduction")
	private String whereProduction;
	
	

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getWhereProduction() {
		return whereProduction;
	}

	public void setWhereProduction(String whereProduction) {
		this.whereProduction = whereProduction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getStockEnd() {
		return stockEnd;
	}

	public void setStockEnd(float stockEnd) {
		this.stockEnd = stockEnd;
	}
}
