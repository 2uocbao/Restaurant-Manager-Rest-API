package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Warehouse;

public interface WarehouseRepository {
	public boolean createWarehouse(Warehouse warehouse);

	public Warehouse detailWarehouse(int restaurantId, int branchId, int materialCode);
	
	public List<Warehouse> listWarehouse(int restaurantId, int branchId);
}
