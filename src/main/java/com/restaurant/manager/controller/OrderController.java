package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			return ResponseEntity.status(HttpStatus.OK).body("Bạn không hoạt động nên không thể tạo gọi món");
		} else if (table.getStatus() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("Bàn này đã có người ngồi");
		} else {
			orders.setEmployee(employee);
			orders.setTable(table);
			orders.setDescription(orderRequest.getDescription());
			orders.setStatus(0);
			success = orderService.createOrder(orders) ? true : false;
			orderDetail orderDetail = new orderDetail();
			int i = orderRequest.getFood().size();
			int j = orderRequest.getQuantity().size();
			if (i > j || i < j) {
				return ResponseEntity.status(HttpStatus.OK)
						.body("Xem lại chi tiết order, món ăn và số lượng, món ăn không có số lượng, hoặc ngược lại");
			}

			for (int n = 0; n < i; n++) {
				Food food = foodService.detailFood(Integer.parseInt(orderRequest.getFood().get(n)));
				if (food.getStatus() == 0) {
					return ResponseEntity.status(HttpStatus.OK).body("Mon an nay hien da het");
				}
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

	// lay danh sach order theo status
	@SuppressWarnings("deprecation")
	@GetMapping("/list-order")
	ResponseEntity<?> listOrderByEmployeeId(@RequestParam("employeeId") String employeeId,
			@RequestParam("status") int status) {
		// lay danh sach order
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		List<Orders> listOrder = orderService.listOrderByEmployeeId(employeeId);
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		// duyet theo danh sach order
		for (Orders order : listOrder) {
			if (order.getStatus() == status && order.getCreatedAt().getDate() == date.getDate()
					&& order.getCreatedAt().getMonth() == date.getMonth()
					&& order.getCreatedAt().getYear() == date.getYear()) {
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
				if (order.getStatus() == 1) {
					orderRequest.setStatus("Đã thanh toán");
				} else {
					orderRequest.setStatus("Chưa thanh toán");
				}
				listOrderRequests.add(orderRequest);

			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(listOrderRequests);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusOrder(@RequestParam("tableId") int tableId) {
		String message = null;
		int status = orderService.getStatusByTableId(tableId) == 0 ? 1 : 0;
		if (status == 0) {
			message = orderService.changeStatus(tableId, status) ? "Chưa thanh toán" : "Không thành công";
		} else {
			message = orderService.changeStatus(tableId, status) ? "Đã thanh toán" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailOrder(@RequestParam("tableId") int tableId) {
		OrderRequest orderRequest = new OrderRequest();
		Orders order = orderService.detailOrder(tableId);
		Tables table = tableService.detailTable(tableId);
		if (table == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu của bàn này");
		} else if (order == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có order nào của bàn này");
		}
		List<orderDetail> listorderDetail = orderDetailService.listOrderbyIdorder(order.getId());
		List<String> foodName = new ArrayList<>();
		List<Integer> quantity = new ArrayList<>();

		HashMap<String, String> listFood = new HashMap<>();
		
		for (orderDetail orderDetail : listorderDetail) {
			Food food = foodService.detailFood(orderDetail.getFood().getId());
			foodName.add(food.getName());
			quantity.add(orderDetail.getQuatity());
			listFood.put(("name"),food.getName() + " " + String.valueOf(orderDetail.getQuatity()));
		}
		orderRequest.setTableId(table.getName());
		orderRequest.setFood(foodName);
		orderRequest.setQuantity(quantity);
		orderRequest.setDescription(order.getDescription());
		if (order.getStatus() == 1) {
			orderRequest.setStatus("Đã thanh toán");
		} else {
			orderRequest.setStatus("Chưa thanh toán");
		}
		return ResponseEntity.status(HttpStatus.OK).body(listFood);
	}

	@GetMapping("/pay")
	ResponseEntity<String> payOrder(@RequestParam("employeeId") String employeeId,
			@RequestParam("tableId") int tableId) {
		float total = 0;
		Orders orders = orderService.detailOrders(employeeId, tableId, 0);
		if (orders == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu order của bàn này, vui lòng xem lại");
		} else if (orders.getStatus() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("Order này đã thanh toán");
		}
		List<orderDetail> listorderDetail = orderDetailService.listOrderbyIdorder(orders.getId());
		List<Integer> costFood = new ArrayList<>();
		List<Integer> listQuantity = new ArrayList<>();
		for (orderDetail orderdetail : listorderDetail) {
			Food food = foodService.detailFood(orderdetail.getFood().getId());
			costFood.add(food.getPrice());
			listQuantity.add(orderdetail.getQuatity());
		}
		for (int i = 0; i < costFood.size(); i++) {
			total = total + costFood.get(i) * listQuantity.get(i);
		}
		orders.setTotalAmount(total);
		orders.setStatus(1);
		orderService.updateOrder(orders);
		tableService.changeStatusById(tableId, 0);
		return ResponseEntity.status(HttpStatus.OK).body("Số tiền cần thanh toán là" + total);
	}

	@PutMapping("/update")
	ResponseEntity<?> updateOrder(@RequestBody OrderRequest orderRequest) {
		String message = null;
		Orders order = orderService.detailOrder(Integer.parseInt(orderRequest.getTableId()));
		Tables table = tableService.detailTable(Integer.parseInt(orderRequest.getTableId()));
		if (table.getStatus() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Bàn này chưa có order");
		} else if (order == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có order này");
		} else if (order.getStatus() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("order này đã thanh toán, không thể cập nhật");
		}
		// update cho order
		order.setDescription(orderRequest.getDescription());
		message = orderService.updateOrder(order) ? "Cập nhật thông tin thành công" : "Không thành công";
		// update orderdetail
		List<orderDetail> listOrderDetails = orderDetailService.listOrderbyIdorder(order.getId());
		List<Integer> listFoodReq = new ArrayList<>();
		HashMap<Integer, Integer> listRequest = new HashMap<>();
		List<Integer> listFoodId = new ArrayList<>();
		List<Integer> listFoodId2 = new ArrayList<>();
		List<Integer> listQuantity = new ArrayList<>();
		for (orderDetail orderdetail : listOrderDetails) {
			listFoodId.add(orderdetail.getFood().getId());
			listFoodId2.add(orderdetail.getFood().getId());
			listQuantity.add(orderdetail.getQuatity());
		}
		for (int i = 0; i < orderRequest.getFood().size(); i++) {
			listFoodReq.add(Integer.parseInt(orderRequest.getFood().get(i)));
			listRequest.put(Integer.parseInt(orderRequest.getFood().get(i)), orderRequest.getQuantity().get(i));
		}
		// lay ra mon muon loai bo
		listFoodId.removeAll(listFoodReq);
		// lay ra mon moi muon them vao
		listFoodReq.removeAll(listFoodId2);
		for (Integer listfoodid : listFoodId) {
			listFoodId2.remove(listfoodid);
		}
		// loai bo mon an khong muon order
		for (Integer foodid : listFoodId) {
			orderDetailService.deleteOrderDetail(order.getId(), foodid);
		}
		// thay doi so luong cua mot mon an
		orderDetail orderDetail = new orderDetail();
		for (Integer listfoodid2 : listFoodId2) {
			Food food = foodService.detailFood(listfoodid2);
			if (food.getStatus() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Mon an nay hien da het");
			}
			orderDetail = orderDetailService.detailOrder(order.getId(), listfoodid2);
			orderDetail.setFood(food);
			orderDetail.setOrder(order);
			orderDetail.setQuatity(listRequest.get(listfoodid2));
			orderDetailService.updateOrderDetail(orderDetail);
		}
		// tao mon moi khi order them
		for (Integer foodid : listFoodReq) {
			Food food = foodService.detailFood(foodid);
			if (food.getStatus() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Mon an nay hien da het");
			}
			orderDetail.setFood(food);
			orderDetail.setOrder(order);
			orderDetail.setQuatity(listRequest.get(foodid));
			orderDetailService.createOrderDetail(orderDetail);
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
