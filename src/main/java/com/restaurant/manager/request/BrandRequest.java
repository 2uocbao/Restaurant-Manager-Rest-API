package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrandRequest {

	@JsonProperty("name")
	private String name;

	@JsonProperty("banner")
	private String banner;

	@JsonProperty("description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}