package com.restaurant.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.repository.WarehouseRepository;
import com.restaurant.manager.service.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
	WarehouseRepository warehouseRepository;

	@Override
	public boolean createWarehouse(Warehouse warehouse) {
		return warehouseRepository.createWarehouse(warehouse);
	}

	@Override
	public Warehouse detailWarehouse(String employeeId, String materialCode) {
		return warehouseRepository.detailWarehouse(employeeId, materialCode);
	}
}
