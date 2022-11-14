package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderRequest {
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("table")
	private String tableId;

	@JsonProperty("food")
	private List<String> food;

	@JsonProperty("quantity")
	private List<Integer> quantity;

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("status")
	private String status;

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

	public List<String> getFood() {
		return food;
	}

	public void setFood(List<String> food) {
		this.food = food;
	}

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
