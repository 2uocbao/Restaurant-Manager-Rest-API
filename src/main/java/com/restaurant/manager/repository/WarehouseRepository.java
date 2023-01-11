package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Warehouse;

public interface WarehouseRepository {
	public boolean createWarehouse(Warehouse warehouse);

	public Warehouse detailWarehouse(String employeeId, String materialCode);
	
	public List<Warehouse> listWarehouse(String employeeId);
}
