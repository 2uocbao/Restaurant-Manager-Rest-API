package com.restaurant.manager.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportRequest {

	//doanh số
	@JsonProperty("turnover")
	private float turnover;

	//giá cả
	@JsonProperty("cost")
	private float cost;

	//lợi nhuận
	@JsonProperty("profit")
	private float protfit;

	public float getTurnover() {
		return turnover;
	}

	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getProtfit() {
		return protfit;
	}

	public void setProtfit(float protfit) {
		this.protfit = protfit;
	}
}
