package com.restaurant.manager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.request.foodOrderRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.FoodService;
import com.restaurant.manager.service.MaterialService;
import com.restaurant.manager.service.OrderService;
import com.restaurant.manager.service.TableService;
import com.restaurant.manager.service.WarehouseDetailService;
import com.restaurant.manager.service.WarehouseService;
import com.restaurant.manager.service.orderDetailService;

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
	@Autowired
	orderDetailService orderDetailService;
	@Autowired
	FoodService foodService;
	@Autowired
	TableService tableService;

	OrderController orderController = new OrderController();

	@SuppressWarnings("deprecation")
	@GetMapping("/in-day")
	ResponseEntity<BaseResponse> reportTurnoverInDay(@RequestParam("employeeId") String employeeId,
			@RequestParam("day") String day, @RequestParam("status") int status) throws ParseException {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId, status);
		float total = 0;
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getDate() == date.getDate() && order.getCreatedAt().getMonth() == date.getMonth()
					&& order.getCreatedAt().getYear() == date.getYear()) {
				total = total + order.getTotalAmount();
				listOrderRequests.add(orderRequests(order));
			}
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(listOrderRequests);
		baseResponse.setMessage("Doanh thu ngày " + date + "là: " + total);
		baseResponse.setStatus(1);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/in-month")
	ResponseEntity<BaseResponse> reportTurnoverInMonth(@RequestParam("employeeId") String employeeId,
			@RequestParam("day") String day) throws ParseException {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId, 1);
		float total = 0;
		Date date = new SimpleDateFormat("yyyy/MM/dd").parse(day);
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		int month = 0;
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getMonth() == date.getMonth()
					&& order.getCreatedAt().getYear() == date.getYear()) {
				listOrderRequests.add(orderRequests(order));
				total = total + order.getTotalAmount();
//				month = order.getCreatedAt().getMonth() + 1;
			}
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(listOrderRequests);
		baseResponse.setMessage("Doanh thu thang " + month + " là: " + total);
		baseResponse.setStatus(1);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/by-year")
	ResponseEntity<BaseResponse> reportTurnoverInYear(@RequestParam("employeeId") String employeeId,
			@RequestParam("year") String year) throws ParseException {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Orders> listOrder = orderService.listOrder(employee.getRestaurant().getId(), branchId, 1);
		float total = 0;
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(year);
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		for (Orders order : listOrder) {
			if (order.getCreatedAt().getYear() == date1.getYear()) {
				total = total + order.getTotalAmount();
				listOrderRequests.add(orderRequests(order));
			}
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(listOrderRequests);
		baseResponse.setMessage("Doanh thu nam " + date1 + " là: " + total);
		baseResponse.setStatus(1);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/material-out-of-stock")
	ResponseEntity<Object> reportmaterialRunout(@RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Material> listMaterials = materialService.listMaterial(employee.getRestaurant().getId(), branchId);
		List<String> listCode = new ArrayList<>();
		for (Material material : listMaterials) {
			if (material.getQuantity() <= material.getStockEnd()) {
				listCode.add(material.getName());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("Những nguyên liệu sắp hết cần nhập hàng ngay" + listCode);
	}

	public OrderRequest orderRequests(Orders order) {
		List<orderDetail> listOrderDetails = orderDetailService.listOrderbyIdorder(order.getId());
		// list mon an
		// list so luong order
		List<foodOrderRequest> listFoodOrderRequests = new ArrayList<>();
		// lay ra danh sach mon an va so luong chinh xac order cua tung mon
		for (orderDetail orderdetail : listOrderDetails) {
			Food food = foodService.detailFood(orderdetail.getFood().getId());
			foodOrderRequest foodOrderRequest = new foodOrderRequest();
			foodOrderRequest.setFood(food.getName());
			foodOrderRequest.setQuantity(orderdetail.getQuatity());
			foodOrderRequest.setPrice(food.getPrice());
			foodOrderRequest.setTotal(orderdetail.getQuatity() * food.getPrice());
			listFoodOrderRequests.add(foodOrderRequest);
		}
		// tao orderrequest, set cac thuoc tinh
		OrderRequest orderRequest = new OrderRequest();
		Tables table = tableService.detailTable(order.getTable().getId());
		orderRequest.setRestaurantId(order.getRestaurant().getId());
		orderRequest.setBranchId(order.getBranch() != null ? order.getBranch().getId() : null);
		orderRequest.setEmployeeId(order.getEmployee().getId());
		orderRequest.setTableId(table.getName());
		orderRequest.setOrderId(order.getId());
		orderRequest.setDescription(order.getDescription());
		orderRequest.setFoodQuantity(listFoodOrderRequests);
		orderRequest.setTotalAmount(order.getTotalAmount());
		orderRequest.setStatus(order.getStatus());
		orderRequest.setCreateAt(order.getCreatedAt());
		return orderRequest;
	}

}
