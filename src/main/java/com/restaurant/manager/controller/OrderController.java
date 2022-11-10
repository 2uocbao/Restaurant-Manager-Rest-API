package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.FoodService;
import com.restaurant.manager.service.OrderService;
import com.restaurant.manager.service.RestaurantService;
import com.restaurant.manager.service.TableService;
import com.restaurant.manager.service.orderDetailService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;

	@Autowired
	RestaurantService restaurService;

	@Autowired
	BranchService branchService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	TableService tableService;

	@Autowired
	orderDetailService orderDetailService;

	@Autowired
	FoodService foodService;

	@PostMapping("/create")
	ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
		boolean success;
		String message = null;
		Orders orders = new Orders();
		Employee employee = employeeService.detailEmployee(orderRequest.getEmployeeId());
		Tables table = tableService.detailTable(Integer.parseInt(orderRequest.getTableId()));
		if (employee.getStatus() == 0) {
			return ResponseEntity.badRequest().body("Bạn không hoạt động nên không thể tạo gọi món");
		} else if (table.getStatus() == 1) {
			return ResponseEntity.badRequest().body("Bàn này đã có người ngồi");
		} else {
			orders.setEmployee(employee);
			orders.setTable(table);
			orders.setDescription(orderRequest.getDescription());
			orders.setStatus(1);
			success = orderService.createOrder(orders) ? true : false;
			orderDetail orderDetail = new orderDetail();
			int i = orderRequest.getFood().size();
			int j = orderRequest.getQuantity().size();
			if (i > j || i < j) {
				return ResponseEntity.badRequest()
						.body("Xem lại chi tiết order, món ăn và số lượng, món ăn không có số lượng, hoặc ngược lại");
			}

			for (int n = 0; n < i; n++) {
				Food food = foodService.detailFood(Integer.parseInt(orderRequest.getFood().get(n)));
				orderDetail.setFood(food);
				orderDetail.setOrder(orders);
				orderDetail.setQuatity(orderRequest.getQuantity().get(n));
				orderDetailService.createOrderDetail(orderDetail);
			}

			tableService.changeStatusById(table.getId(), 1);
			message = success ? "Việc gọi món đã hoàn tất" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/list-order")
	ResponseEntity<?> listOrderByEmployeeId(@RequestParam("employeeId") String employeeId) {
		// lay danh sach order
		List<Orders> listOrder = orderService.listOrderByEmployeeId(employeeId);
		
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		
		// duyet theo danh sach order
		for (Orders order : listOrder) {
			
			// voi moi order lay ra danh sach cac orderdetail tuong ung voi idorder
			List<orderDetail> listOrderDetails = orderDetailService.listOrderbyIdorder(order.getId());

			// list mon an
			List<String> nameFood = new ArrayList<>();

			// list so luong order
			List<Integer> quantityFood = new ArrayList<>();

			// lay ra danh sach mon an va so luong chinh xac order cua tung mon
			for (orderDetail orderdetail : listOrderDetails) {
				Food food = foodService.detailFood(orderdetail.getFood().getId());
				nameFood.add(food.getName());
				quantityFood.add(orderdetail.getQuatity());
			}
			
			// tao orderrequest, set cac thuoc tinh
			OrderRequest orderRequest = new OrderRequest();
			Tables table = tableService.detailTable(order.getTable().getId());
			orderRequest.setTableId(table.getName());
			orderRequest.setDescription(order.getDescription());
			
			// su dung list namefood da lay ra o tren
			orderRequest.setFood(nameFood);
			
			// su dung quantityFood da lay o tren
			orderRequest.setQuantity(quantityFood);
			listOrderRequests.add(orderRequest);

		}
		
		int status = 0;
		
		if(status == 0) {
			
		}

		return ResponseEntity.status(HttpStatus.OK).body(listOrderRequests);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/list-by-status")
	ResponseEntity<?> listOrderByStatus(@RequestParam("employeeId") String employeeId,
			@RequestParam("status") int status) {
		List<Orders> listOrder = orderService.listOrderONorOFF(employeeId, status);
		
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(listOrder);
	}

	@PostMapping("/change-status")
	ResponseEntity<String> changeStatusOrder(@RequestParam("tableName") int tableId) {
		String message = null;
		int status = orderService.getStatusByTableId(tableId) == 0 ? 1 : 0;

		message = orderService.changeStatus(tableId, status) ? "Cập nhật trạng thái thành công" : "Không thành công";

		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailOrder(@RequestParam("orderId") int orderId) {
		OrderRequest orderRequest = new OrderRequest();
		List<orderDetail> listorderDetail = orderDetailService.listOrderbyIdorder(orderId);
		List<String> foodName = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();
		for (orderDetail orderDetail : listorderDetail) {
			foodName.add(orderDetail.getFood().getName());
			quantity.add(orderDetail.getQuatity());
		}
		orderRequest.setFood(foodName);
		orderRequest.setQuantity(quantity);
		return ResponseEntity.status(HttpStatus.OK).body(orderRequest);
	}

	@GetMapping("/pay")
	ResponseEntity<String> payOrder(@RequestParam("orderId") int orderId) {
		float total = 0;
		List<orderDetail> listorderDetail = orderDetailService.listOrderbyIdorder(orderId);
		List<Integer> costFood = new ArrayList<>();
		List<Integer> listQuantity = new ArrayList<>();
		for (orderDetail orderdetail : listorderDetail) {
			Food food = foodService.detailFood(orderdetail.getFood().getId());
			costFood.add(food.getPrice());
			listQuantity.add(orderdetail.getQuatity());
		}
		for (Integer price : costFood) {
			for (Integer quantity : listQuantity) {
				total += price * quantity;
			}
		}
		Orders orders = orderService.detailOrder(orderId);
		orders.setTotalAmount(total);
		orders.setStatus(1);
		orderService.updateOrder(orders);
		return ResponseEntity.status(HttpStatus.OK).body("Số tiền cần thanh toán là" + total);
	}
}
