package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.WarehouseRequest;

public interface WarehouseService {
	public String createWarehouse(int materailId, WarehouseRequest warehouseRequest);

	public List<WarehouseRequest> detailWarehouse(String employeeId, String materialCode);

}
