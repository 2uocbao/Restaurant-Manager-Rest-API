package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialFood {
	@JsonProperty("MaterialFood")
	private List<MaterialFoodRequest> materialFoodRequests;

	public List<MaterialFoodRequest> getMaterialFoodRequests() {
		return materialFoodRequests;
	}

	public void setMaterialFoodRequests(List<MaterialFoodRequest> materialFoodRequests) {
		this.materialFoodRequests = materialFoodRequests;
	}
}
