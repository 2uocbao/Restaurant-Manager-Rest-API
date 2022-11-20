package com.restaurant.manager.request;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
	@JsonProperty("restaurantId")
	private String restaurantId;
	
	@JsonProperty("branchId")
	private String branchId;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("table")
	private String tableId;
	
	@JsonProperty("orderId")
	private int orderId;

	@JsonProperty("food")
	private List<foodOrderRequest> foodQuantity;

	@JsonProperty("totalAmount")
	private float totalAmount;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("createAt")
	private Timestamp createAt;
	
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<foodOrderRequest> getFoodQuantity() {
		return foodQuantity;
	}

	public void setFoodQuantity(List<foodOrderRequest> foodQuantity) {
		this.foodQuantity = foodQuantity;
	}

	public int getStatus() {
		return status;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
}
