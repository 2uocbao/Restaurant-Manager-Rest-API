package com.restaurant.manager.repository;

import com.restaurant.manager.model.Warehouse;

public interface WarehouseRepository {
	public boolean createWarehouse(Warehouse warehouse);

	public Warehouse detailWarehouse(String employeeId, String materialCode);
}
