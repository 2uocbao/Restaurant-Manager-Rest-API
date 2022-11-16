package com.restaurant.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "material")
@DynamicUpdate
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "namemate")
	private String name;

	@Column(name = "price")
	private int cost;
	
	@Column(name = "typemate")
	private String type;

	@Column(name = "quantity")
	private float quantity;
	
	@Column(name = "stockEnd")
	private float stockEnd;

	@Column(name = "where_production")
	private String whereProduction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Restaurants restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Branch branch;

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

	public Restaurants getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurants restaurant) {
		this.restaurant = restaurant;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getStockEnd() {
		return stockEnd;
	}

	public void setStockEnd(float stockEnd) {
		this.stockEnd = stockEnd;
	}
}
