package com.restaurant.manager.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {

	@JsonProperty("employeeId")
	private int employeeId;

	@JsonProperty("tableId")
	private int tableId;

	@JsonProperty("orderId")
	private int orderId;

	@JsonProperty("totalAmount")
	private float totalAmount;

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private int status;

	@JsonProperty("createAt")
	private Timestamp createAt;

	@JsonProperty("updatedAt")
	private Timestamp updatedAt;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
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

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
