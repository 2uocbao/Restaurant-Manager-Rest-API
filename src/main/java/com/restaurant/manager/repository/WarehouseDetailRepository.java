package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.WarehouseDetail;

public interface WarehouseDetailRepository {
	public boolean createWarehouseDetail(WarehouseDetail warehouseDetail);

	public float getTotalAmountByMaterialCode(String materialCode);
	
	public List<WarehouseDetail> listWarehouseDetail(int warehouseId);
}
