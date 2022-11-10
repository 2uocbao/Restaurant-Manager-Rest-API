package com.restaurant.manager.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantRequest {
	
	@JsonProperty("restaurantId")
	private String restaurantId;

	@NotEmpty(message = "Tên không được để trống")
	@Size(min = 2, max = 32, message = "Tên phải dài từ 2 đến 32 ký tự")
	@JsonProperty("name")
	private String name;

	@NotNull(message = "Email không được để trống")
	@JsonProperty("email")
	private String email;

	@NotEmpty(message = "Số điện thoại không được để trống")
	@Size(min = 10, max = 10, message = "Số điện thoại không hợp lệ")
	@JsonProperty("phone")
	private String phone;

	@NotNull(message = "Thông tin không được để trống")
	@JsonProperty("info")
	private String info;

	@NotEmpty(message = "Địa chỉ không được để trống")
	@JsonProperty("address")
	private String address;
	
	

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
