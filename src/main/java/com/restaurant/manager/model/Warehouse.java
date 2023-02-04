package com.restaurant.manager.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// nhiều lần nhập kho với một nhân viên
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Employee employee;

	// nguyên liệu sẽ được khởi tạo lần đầu nhập
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "material_id", referencedColumnName = "id")
	private Material material;

	// một nhà kho có nhiều nhà kho chi tiết
	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<WarehouseDetail> warehouseDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Collection<WarehouseDetail> getWarehouseDetail() {
		return warehouseDetail;
	}

	public void setWarehouseDetail(Collection<WarehouseDetail> warehouseDetail) {
		this.warehouseDetail = warehouseDetail;
	}
}
