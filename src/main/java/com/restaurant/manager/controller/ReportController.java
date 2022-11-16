package com.restaurant.manager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.MaterialService;
import com.restaurant.manager.service.OrderService;
import com.restaurant.manager.service.WarehouseDetailService;
import com.restaurant.manager.service.WarehouseService;

@RestController
@RequestMapping("/report")
public class ReportController {
	@Autowired
	OrderService orderService;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	WarehouseDetailService warehouseDetailService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	MaterialService materialService;

	@SuppressWarnings("deprecation")
	@PostMapping("/in-day")
	ResponseEntity<String> reportTurnoverInDay(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId);
		float total = 0;
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getDate() == date.getDate() && order.getCreatedAt().getMonth() == date.getMonth()
					&& order.getCreatedAt().getYear() == date.getYear()) {
				total = total + order.getTotalAmount();
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body("Doanh thu " + date.getDate() + "/" + date.getMonth() + "/" + date.getYear() + " là: " + total);
	}

	@SuppressWarnings("deprecation")
	@PostMapping("/in-month")
	ResponseEntity<String> reportTurnoverInMonth(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId);
		float total = 0;
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getMonth() <= date.getMonth()
					&& order.getCreatedAt().getYear() <= date.getYear()) {
				total = total + order.getTotalAmount();
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body("Doanh thu thang " + date.getMonth() + "/" + date.getYear() + " là: " + total);
	}

	@SuppressWarnings("deprecation")
	@PostMapping("/by-year")
	ResponseEntity<String> reportTurnoverInYear(@RequestParam("employeeId") String employeeId,
			@RequestParam("year") String year) throws ParseException {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId);
		float total = 0;
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(year);
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getYear() == date1.getYear()) {
				total = total + order.getTotalAmount();
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("Doanh thu nam " + year + " là: " + total);
	}

	@PostMapping("/material-out-of-stock")
	ResponseEntity<Object> reportmaterialRunout(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Material> listMaterials = materialService.listMaterial(employee.getRestaurant().getId(), branchId);
		List<String> listCode = new ArrayList<>();
		for (Material material : listMaterials) {
			if (material.getQuantity() <= material.getStockEnd()) {
				listCode.add(material.getCode());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("Những nguyên liệu sắp hết cần nhập hàng ngay" + listCode);
	}
}
