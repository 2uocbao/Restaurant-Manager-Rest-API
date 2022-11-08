package com.restaurant.manager.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarehouseRequest {
	@JsonProperty("material code")
	private String materialCode;

	@JsonProperty("vat amount")
	private BigDecimal vatAmount;

	@JsonProperty("cost")
	private int cost;

	@JsonProperty("quantity")
	private int quantity;
	
	@JsonProperty("date")
	private Timestamp date;

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
