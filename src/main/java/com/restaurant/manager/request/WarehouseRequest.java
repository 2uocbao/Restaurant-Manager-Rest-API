package com.restaurant.manager.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarehouseRequest {
	@JsonProperty("employeeId")
	private int employeeId;

	@JsonProperty("restaurantId")
	private int restaurantId;

	@JsonProperty("branchId")
	private int branchId;

	@JsonProperty("materialId")
	private int materialId;

	@JsonProperty("vatAmount")
	private BigDecimal vatAmount;

	@JsonProperty("cost")
	private int cost;

	@JsonProperty("quantity")
	private float quantity;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private int status;

	@JsonProperty("date")
	private Timestamp date;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
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

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
