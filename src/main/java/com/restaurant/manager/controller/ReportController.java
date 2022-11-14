//package com.restaurant.manager.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.restaurant.manager.model.Employee;
//import com.restaurant.manager.model.Material;
//import com.restaurant.manager.model.Orders;
//import com.restaurant.manager.service.EmployeeService;
//import com.restaurant.manager.service.MaterialService;
//import com.restaurant.manager.service.OrderService;
//import com.restaurant.manager.service.WarehouseDetailService;
//import com.restaurant.manager.service.WarehouseService;
//
//@RestController
//@RequestMapping("/report")
//public class ReportController {
//	@Autowired
//	OrderService orderService;
//	@Autowired
//	WarehouseService warehouseService;
//	@Autowired
//	WarehouseDetailService warehouseDetailService;
//	@Autowired
//	EmployeeService employeeService;
//	@Autowired
//	MaterialService materialService;
//
//	@SuppressWarnings("deprecation")
//	@PostMapping("/inDay")
//	ResponseEntity<String> reportTurnoverInDay(@RequestParam("employeeId") String employeeId) {
//		List<Orders> listOrder = orderService.listOrderByEmployeeId(employeeId);
//		float total = 0;
//		long millis = System.currentTimeMillis();
//		java.sql.Date date = new java.sql.Date(millis);
//		for (Orders order : listOrder) {
//			if (order.getCreatedAt().getDate() == date.getDate() && order.getCreatedAt().getMonth() == date.getMonth()
//					&& order.getCreatedAt().getYear() == date.getYear()) {
//				total = total + order.getTotalAmount();
//			}
//		}
//		return ResponseEntity.status(HttpStatus.OK)
//				.body("Doanh thu " + date.getDate() + "/" + date.getMonth() + "/" + date.getYear() + " là: " + total);
//	}
//
//	@SuppressWarnings("deprecation")
//	@PostMapping("/inMonth")
//	ResponseEntity<String> reportTurnoverInMonth(@RequestParam("employeeId") String employeeId) {
//		List<Orders> listOrder = orderService.listOrderByEmployeeId(employeeId);
//		float total = 0;
//		long millis = System.currentTimeMillis();
//		java.sql.Date date = new java.sql.Date(millis);
//		for (Orders order : listOrder) {
//			if (order.getCreatedAt().getMonth() == date.getMonth()
//					&& order.getCreatedAt().getYear() == date.getYear()) {
//				total = total + order.getTotalAmount();
//			}
//		}
//		return ResponseEntity.status(HttpStatus.OK)
//				.body("Doanh thu thang " + date.getMonth() + "/" + date.getYear() + " là: " + total);
//	}
//
//	@PostMapping("/material-out-of-stock")
//	ResponseEntity<String> reportmaterialRunout(@RequestParam("employeeId") String employeeId) {
//		Employee employee = employeeService.detailEmployee(employeeId);
//		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
//		List<Material> listMaterials = materialService.listMaterial(employee.getRestaurant().getId(), branchId);
//		for (Material material : listMaterials) {
//			if (material.getQuantity() < tmp) {
//				// out of stock in material
//			}
//		}
//	}
//}
