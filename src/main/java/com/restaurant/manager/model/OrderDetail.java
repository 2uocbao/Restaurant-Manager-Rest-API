package com.restaurant.manager.model;

import java.io.Serializable;

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
@Table(name = "order_detail")
@DynamicUpdate
public class OrderDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// nhiều gọi món chi tiết với một gọi món
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Orders orders;

	// một gọi món chi tiết có nhiều món ăn
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Food food;

	@Column(name = "quantity")
	private int quatity;
	
	@Column(name = "status")
	private int status;

	public Orders getOrder() {
		return orders;
	}

	public void setOrder(Orders orders) {
		this.orders = orders;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getQuatity() {
		return quatity;
	}

	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
