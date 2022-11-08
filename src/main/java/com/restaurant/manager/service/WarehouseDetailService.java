package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.WarehouseDetail;

public interface WarehouseDetailService {
	public boolean createWarehouseDetail(WarehouseDetail warehouseDetail);

	public float getTotalAmountByMaterialCode(String materialCode);
	
	public List<WarehouseDetail> listWarehouseDetail(int warehouseId);

}
