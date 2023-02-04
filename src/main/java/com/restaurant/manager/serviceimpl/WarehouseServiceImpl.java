package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.WarehouseDetailRepository;
import com.restaurant.manager.repository.WarehouseRepository;
import com.restaurant.manager.request.WarehouseRequest;
import com.restaurant.manager.service.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	WarehouseDetailRepository warehouseDetailRepository;
	@Autowired
	MaterialRepository materialRepository;

	private String success = "success";

	@Override
	public String createWarehouse(int materialId, WarehouseRequest warehouseRequest) {
		boolean successful = false;
		Warehouse warehouse1 = new Warehouse();
		Employee employee = employeeRepository.detailEmployee(warehouseRequest.getEmployeeId());
		Material material = materialRepository.detailMaterial(materialId);
		Warehouse warehouse = warehouseRepository.detailWarehouse(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), material.getId());
		if (warehouse == null) {
			warehouse1.setEmployee(employee);
			warehouse1.setMaterial(material);
			warehouseRepository.createWarehouse(warehouse1);
		}
		WarehouseDetail warehouseDetail = new WarehouseDetail();
		warehouseDetail.setWarehouse(warehouse == null ? warehouse1 : warehouse);
		warehouseDetail.setCost(warehouseRequest.getCost());
		warehouseDetail.setVatAmount(warehouseRequest.getVatAmount());
		warehouseDetail.setQuantity(warehouseRequest.getQuantity());
		warehouseDetail.setTotalAmount(warehouseRequest.getQuantity() + material.getQuantity());
		warehouseDetail.setDescription(warehouseRequest.getDescription());
		warehouseDetail.setStatus(1);
		material.setCost(warehouseDetail.getCost());
		material.setQuantity(warehouseDetail.getTotalAmount());
		materialRepository.updateMaterial(material);
		successful = warehouseDetailRepository.createWarehouseDetail(warehouseDetail);
		return successful ? success : "No success";
	}

	@Override
	public List<WarehouseRequest> listWarehouse(int employeeId, int materialId) {
		Employee employee = employeeRepository.detailEmployee(employeeId);
		Warehouse warehouse = warehouseRepository.detailWarehouse(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), materialId);
		List<WarehouseDetail> warehouseDetails = warehouseDetailRepository.listWarehouseDetail(warehouse.getId());
		List<WarehouseRequest> warehouseRequests = new ArrayList<>();
		for (WarehouseDetail warehouseD : warehouseDetails) {
			WarehouseRequest warehouseRequest = new WarehouseRequest();
			warehouseRequest.setEmployeeId(warehouse.getEmployee().getId());
			warehouseRequest.setMaterialId(warehouse.getMaterial().getId());
			warehouseRequest.setQuantity(warehouseD.getQuantity());
			warehouseRequest.setCost(warehouseD.getCost());
			warehouseRequest.setVatAmount(warehouseD.getVatAmount());
			warehouseRequest.setDescription(warehouseD.getDescription());
			warehouseRequest.setStatus(warehouseD.getStatus());
			warehouseRequest.setDate(warehouseD.getCreateAt());
			warehouseRequests.add(warehouseRequest);
		}
		return warehouseRequests;
	}
}
