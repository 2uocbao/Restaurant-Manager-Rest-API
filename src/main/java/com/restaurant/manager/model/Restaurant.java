package com.restaurant.manager.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicUpdate;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table(name = "restaurant")
@Entity
@DynamicUpdate
public class Restaurant {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "namerest")
	private String name;

	@Column(name = "email")
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "info")
	private String info;

	@Column(name = "image")
	private String image;

	@Column(name = "address")
	private String address;

	@Column(name = "status")
	private int status;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Branch> branch;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Employee> employee;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Tables> table;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Food> food;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}