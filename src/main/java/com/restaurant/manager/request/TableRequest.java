package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableRequest {
	
	@JsonProperty("restaurantId")
	private String restaurantId;
	
	@JsonProperty("branchId")
	private String branchId;
	
	@JsonProperty("tableId")
	private int tableId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("totalSlot")
	private int totalSlot;
	
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

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	public int getTableId() {
		return tableId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalSlot() {
		return totalSlot;
	}

	public void setTotalSlot(int totalSlot) {
		this.totalSlot = totalSlot;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
