package com.restaurant.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "report_entity")
public class ReportEntity {
	@Id
	private int id;

	@Column(name = "restaurant_id")
	private int restaurantId;

	@Column(name = "branch_id")
	private int branchId;

	@Column(name = "spend")
	private float spend;

	@Column(name = "income")
	private float income;

	@Column(name = "profit")
	private float profit;

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public float getSpend() {
		return spend;
	}

	public void setSpend(float spend) {
		this.spend = spend;
	}

	public float getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}
}
