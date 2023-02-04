package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialFoodRequest {
	@JsonProperty("materialId")
	private int material;

	@JsonProperty("quantity")
	private float quantity;

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
}
