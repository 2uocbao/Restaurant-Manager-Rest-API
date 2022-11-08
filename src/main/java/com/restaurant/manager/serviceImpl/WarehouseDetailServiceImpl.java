package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.repository.WarehouseDetailRepository;
import com.restaurant.manager.service.WarehouseDetailService;

@Service
public class WarehouseDetailServiceImpl implements WarehouseDetailService {

	@Autowired
	WarehouseDetailRepository warehouseDetailRepository;

	@Override
	public boolean createWarehouseDetail(WarehouseDetail warehouseDetail) {
		return warehouseDetailRepository.createWarehouseDetail(warehouseDetail);
	}

	@Override
	public float getTotalAmountByMaterialCode(String materialCode) {
		return warehouseDetailRepository.getTotalAmountByMaterialCode(materialCode);
	}

	@Override
	public List<WarehouseDetail> listWarehouseDetail(int warehouseId) {
		return warehouseDetailRepository.listWarehouseDetail(warehouseId);
	}

	
}
