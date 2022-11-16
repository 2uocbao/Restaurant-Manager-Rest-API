package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class materialFood {
	@JsonProperty("material")
	private String materialN;
	
	@JsonProperty("quantity")
	private float quantity;

	public String getMaterial() {
		return materialN;
	}

	public void setMaterial(String materialN) {
		this.materialN = materialN;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
}
