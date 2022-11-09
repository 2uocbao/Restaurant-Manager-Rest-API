package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableRequest {
	
	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("total_slot")
	private int totalSlot;

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
}
