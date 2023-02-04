package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchRequest {

	@JsonProperty("branchId")
	private int branchId;

	@JsonProperty("restaurantId")
	private int restaurantId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("info")
	private String info;
	
	@JsonProperty("logo")
	private String logo;

	@JsonProperty("street")
	private String street;

	@JsonProperty("address")
	private String address;

	@JsonProperty("phone")
	private String phone;
	
	@JsonProperty("status")
	private int status;

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
