package com.restaurant.manager.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.request.WarehouseRequest;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.MaterialService;
import com.restaurant.manager.service.WarehouseDetailService;
import com.restaurant.manager.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	@Autowired
	WarehouseService warehouseService;

	@Autowired
	WarehouseDetailService warehouseDetailService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	MaterialService materialService;

	@PostMapping("/create")
	ResponseEntity<String> createWarehouse(@RequestParam("employeeId") String employeeId,
			@RequestBody WarehouseRequest warehouseRequest) {
		String message = null;
		boolean success;
		Employee employee = employeeService.detailEmployee(employeeId);
		Material material = null;
		Warehouse warehouse = new Warehouse();
		if (employee.getStatus() == 0) {
			return ResponseEntity.badRequest().body("Bạn không hoạt động");
		} else if (employee.getBranch() == null) {
			material = materialService.detailMaterial(warehouseRequest.getMaterialCode(),
					employee.getRestaurant().getId(), "");
		} else {
			material = materialService.detailMaterial(warehouseRequest.getMaterialCode(),
					employee.getRestaurant().getId(), employee.getBranch().getId());
		}
		if (material == null) {
			return ResponseEntity.badRequest().body("Nguyên liệu này chưa được tạo");
		} else {
			Warehouse warehouseTmp = warehouseService.detailWarehouse(employee.getId(), material.getCode());
			if (warehouseTmp == null) {
				warehouse.setEmployee(employee);
				warehouse.setMaterialCode(material.getCode());
				warehouseService.createWarehouse(warehouse);
				WarehouseDetail warehouseDetail = new WarehouseDetail();
				warehouseDetail.setWarehouse(warehouse);
				warehouseDetail.setCost(warehouseRequest.getCost());
				warehouseDetail.setVatAmount(warehouseRequest.getVatAmount());
				warehouseDetail.setQuantity(warehouseRequest.getQuantity());
				warehouseDetail.setTotalAmount(warehouseRequest.getQuantity() + material.getQuantity());
				warehouseDetailService.createWarehouseDetail(warehouseDetail);
				material.setQuantity(warehouseDetail.getTotalAmount());
				material.setCost(warehouseDetail.getCost());
			} else {
				WarehouseDetail warehouseDetail = new WarehouseDetail();
				warehouseDetail.setWarehouse(warehouseTmp);
				warehouseDetail.setCost(warehouseRequest.getCost());
				warehouseDetail.setVatAmount(warehouseRequest.getVatAmount());
				warehouseDetail.setQuantity(warehouseRequest.getQuantity());
				warehouseDetail.setTotalAmount(warehouseRequest.getQuantity() + material.getQuantity());
				warehouseDetailService.createWarehouseDetail(warehouseDetail);
				material.setQuantity(warehouseDetail.getTotalAmount());
				material.setCost(warehouseDetail.getCost());
			}
			success = materialService.updateMaterial(material) ? true : false;
			message = success ? "Thành công" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	/// list theo ma material

	@GetMapping("/list-warehouse")
	ResponseEntity<?> listWarehouse(@RequestParam("employeeId") String employeeId,
			@RequestParam("material code") String materialCode) {
		List<WarehouseDetail> listWarehouseDetails = null;
		List<WarehouseRequest> listWarehouseRequests = new ArrayList<>();
		Warehouse warehouse = warehouseService.detailWarehouse(employeeId, materialCode);
		listWarehouseDetails = warehouseDetailService.listWarehouseDetail(warehouse.getId());
		for (WarehouseDetail warehouseD : listWarehouseDetails) {
			WarehouseRequest warehouseRequest = new WarehouseRequest();
			warehouseRequest.setMaterialCode(warehouse.getMaterialCode());
			warehouseRequest.setQuantity(warehouseD.getQuantity());
			warehouseRequest.setCost(warehouseD.getCost());
			warehouseRequest.setVatAmount(warehouseD.getVatAmount());
			warehouseRequest.setDate(warehouseD.getCreateAt());
			listWarehouseRequests.add(warehouseRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listWarehouseRequests);
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/warehouse-in")
	ResponseEntity<?> warehouseDay(@RequestParam("employeeId") String employeeId,
			@RequestParam("material code") String materialCode, @RequestParam("day") String day) {
		List<WarehouseRequest> listWarehouseRequests = new ArrayList<>();
		List<WarehouseDetail> listWarehouseDetails = null;
		Warehouse warehouse = warehouseService.detailWarehouse(employeeId, materialCode);
		listWarehouseDetails = warehouseDetailService.listWarehouseDetail(warehouse.getId());
		for (WarehouseDetail warehouseD : listWarehouseDetails) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = (Date) formatter.parse(day);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (warehouseD.getCreateAt().getDate() == date.getDate()
					&& warehouseD.getCreateAt().getMonth() == date.getMonth()) {
				WarehouseRequest warehouseRequest = new WarehouseRequest();
				warehouseRequest.setMaterialCode(materialCode);
				warehouseRequest.setQuantity(warehouseD.getQuantity());
				warehouseRequest.setCost(warehouseD.getCost());
				warehouseRequest.setVatAmount(warehouseD.getVatAmount());
				warehouseRequest.setDate(warehouseD.getCreateAt());
				listWarehouseRequests.add(warehouseRequest);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listWarehouseRequests);
	}
}
