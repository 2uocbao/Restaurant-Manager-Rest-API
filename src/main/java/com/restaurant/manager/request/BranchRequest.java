package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchRequest {

	@JsonProperty("branchId")
	private String branchId;

	@JsonProperty("restaurantId")
	private String restaurantId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("street")
	private String street;

	@JsonProperty("address")
	private String address;

	@JsonProperty("phone")
	private String phone;
	
	@JsonProperty("status")
	private int status;

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
