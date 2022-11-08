package com.restaurant.manager.service;

import com.restaurant.manager.model.Warehouse;

public interface WarehouseService {
	public boolean createWarehouse(Warehouse warehouse);

	public Warehouse detailWarehouse(String employeeId, String materialCode);

}
