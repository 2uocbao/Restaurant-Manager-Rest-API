package com.restaurant.manager.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.WarehouseDetailRepository;
import com.restaurant.manager.repository.WarehouseRepository;
import com.restaurant.manager.request.MaterialRequest;
import com.restaurant.manager.service.ReportService;

public class ReportServiceImpl implements ReportService {

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	WarehouseDetailRepository warehouseDetailRepository;

	@Override
	public Float incomebyDay(String day, String restaurantId, String branchId) {
		Float total = 0f;
		List<Orders> orders = orderRepository.listOrder(restaurantId, branchId == null ? "" : branchId, 1);
		Date date = date(day);
		for (Orders order : orders) {
			if (date.equals(order.getCreatedAt())) {
				total = total + order.getTotalAmount();
			}
		}
		return total;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Float incomebyMonth(String day, String restaurantId, String branchId) {
		Float total = 0f;
		List<Orders> orders = orderRepository.listOrder(restaurantId, branchId == null ? "" : branchId, 1);
		Date date = date(day);
		for (Orders order : orders) {
			if (date.getDate() == order.getCreatedAt().getDate()) {
				total = total + order.getTotalAmount();
			}
		}
		return total;
	}

	@Override
	public List<MaterialRequest> reportOutofStockMaterial(String restaurantId, String branchId) {
		List<Material> materials = materialRepository.listMaterial(restaurantId, branchId);
		List<MaterialRequest> materialRequests = new ArrayList<>();
		for (Material material : materials) {
			if (material.getStockEnd() >= material.getQuantity()) {
				MaterialRequest materialRequest = new MaterialRequest();
				materialRequest.setRestaurantId(material.getRestaurant().getId());
				materialRequest.setBranchId(material.getBranch() != null ? material.getBranch().getId() : null);
				materialRequest.setCode(material.getCode());
				materialRequest.setName(material.getName());
				materialRequest.setCost(material.getCost());
				materialRequest.setType(material.getType());
				materialRequest.setQuantity(material.getQuantity());
				materialRequest.setStockEnd(material.getStockEnd());
				materialRequest.setWhereProduction(material.getWhereProduction());
				materialRequests.add(materialRequest);
			}
		}
		return materialRequests;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Float spendbyDay(String employeeId) {
		Float spend = 0f;
		List<Warehouse> warehouses = warehouseRepository.listWarehouse(employeeId);
		List<WarehouseDetail> warehouseDetails = new ArrayList<>();
		for (Warehouse warehouse : warehouses) {
			warehouseDetails = warehouseDetailRepository.listWarehouseDetail(warehouse.getId());
		}
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		for (WarehouseDetail warehouseDetail : warehouseDetails) {
			if (date.getDate() == warehouseDetail.getCreateAt().getDate()
					&& date.getMonth() == warehouseDetail.getCreateAt().getMonth()
					&& date.getYear() == warehouseDetail.getCreateAt().getYear()) {
				spend = spend + warehouseDetail.getCost();
			}
		}
		return spend;
	}

	public Date date(String day) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
