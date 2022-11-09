package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialRequest {
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cost")
	private int cost;
	
	@JsonProperty("type")
	private String type;

	@JsonProperty("where_production")
	private String whereProduction;
	
	

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getWhereProduction() {
		return whereProduction;
	}

	public void setWhereProduction(String whereProduction) {
		this.whereProduction = whereProduction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
