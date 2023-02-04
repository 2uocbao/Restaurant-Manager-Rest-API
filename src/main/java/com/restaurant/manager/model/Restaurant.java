package com.restaurant.manager.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table(name = "restaurant")
@Entity
@DynamicUpdate
public class Restaurant implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "info")
	private String info;

	@Column(name = "logo")
	private String logo;

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

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Material> material;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public Collection<Branch> getBranch() {
		return branch;
	}

	public void setBranch(Collection<Branch> branch) {
		this.branch = branch;
	}

	public Collection<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(Collection<Employee> employee) {
		this.employee = employee;
	}

	public Collection<Tables> getTable() {
		return table;
	}

	public void setTable(Collection<Tables> table) {
		this.table = table;
	}

	public Collection<Food> getFood() {
		return food;
	}

	public void setFood(Collection<Food> food) {
		this.food = food;
	}

	public Collection<Material> getMaterial() {
		return material;
	}

	public void setMaterial(Collection<Material> material) {
		this.material = material;
	}
}