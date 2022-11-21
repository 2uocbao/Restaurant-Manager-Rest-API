package com.restaurant.manager.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.request.foodOrderRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.FoodDetailService;
import com.restaurant.manager.service.FoodService;
import com.restaurant.manager.service.MaterialService;
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

	@Autowired
	MaterialService materialService;

	@Autowired
	FoodDetailService foodDetailService;

	@PostMapping("/create")
	ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
		String message = null;
		Orders orders = new Orders();
		Employee employee = employeeService.detailEmployee(orderRequest.getEmployeeId());
		Tables table = tableService.detailTable(Integer.parseInt(orderRequest.getTableId()));
		List<foodOrderRequest> foodOrderList = orderRequest.getFoodQuantity();
		for (foodOrderRequest foodOrderRequest : foodOrderList) {
			Food food = foodService.detailFood(Integer.parseInt(foodOrderRequest.getFood()));
			if (food.getStatus() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Món ăn này đã hết");
			}
		}
		if (table.getStatus() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("Bàn này đã có người ngồi");
		} else {
			orders.setRestaurant(employee.getRestaurant());
			Branch branch = employee.getBranch() != null ? branchService.detailBranch(employee.getBranch().getId())
					: null;
			orders.setBranch(branch);
			orders.setEmployee(employee);
			orders.setTable(table);
			orders.setDescription(orderRequest.getDescription());
			orders.setStatus(0);
			message = orderService.createOrder(orders) ? "Việc gọi món đã hoàn tất" : "";
			orderDetail orderDetail = new orderDetail();
			for (foodOrderRequest foodOrderRequest : foodOrderList) {
				Food food = foodService.detailFood(Integer.parseInt(foodOrderRequest.getFood()));
				orderDetail.setFood(food);
				orderDetail.setOrder(orders);
				orderDetail.setQuatity(foodOrderRequest.getQuantity());
				orderDetailService.createOrderDetail(orderDetail);
			}
			tableService.changeStatusById(table.getId(), 1);
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	// lay danh sach order theo status
	@SuppressWarnings("deprecation")
	@GetMapping("/list-order")
	ResponseEntity<Object> listOrderByEmployeeId(@RequestParam("employeeId") String employeeId,
			@RequestParam("status") int status, @RequestParam("day") String day) throws ParseException {
		// lay danh sach order
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(day);
		List<Orders> listOrder = orderService.listOrderByEmployeeId(employeeId);
		List<OrderRequest> listOrderRequests = new ArrayList<>();
		// duyet theo danh sach order
		for (Orders order : listOrder) {
			if (order.getStatus() == status && order.getCreatedAt().getDate() == date.getDate()
					&& order.getCreatedAt().getMonth() == date.getMonth()
					&& order.getCreatedAt().getYear() == date.getYear()) {
				listOrderRequests.add(orderRequests(order));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listOrderRequests);
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
			foodOrderRequest.setPrice(food.getPrice() * orderdetail.getQuatity());
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

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusOrder(@RequestParam("orderId") int orderId) {
		String message = null;
		int status = orderService.getStatusByOrderId(orderId) == 0 ? 1 : 0;
		if (status == 0) {
			message = orderService.changeStatus(orderId, status) ? "Chưa thanh toán" : "";
		} else {
			message = orderService.changeStatus(orderId, status) ? "Đã thanh toán" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<Object> detailOrder(@RequestParam("employeeId") String employeeId,
			@RequestParam("tableId") int tableId) {
		Tables table = tableService.detailTable(tableId);
		Orders order = orderService.detailOrders(employeeId, table.getId(), 0);
		OrderRequest orderRequest = orderRequests(order);
		return ResponseEntity.status(HttpStatus.OK).body(orderRequest);
	}

	@GetMapping("/pay")
	ResponseEntity<BaseResponse> payOrder(@RequestParam("employeeId") String employeeId,
			@RequestParam("tableId") int tableId) {
		float total = 0;
		BaseResponse baseResponse = new BaseResponse();
		OrderRequest orderRequest = new OrderRequest();
		Orders orders = orderService.detailOrders(employeeId, tableId, 0);
		if (orders.getStatus() == 1) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Order này đã thanh toán");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		List<foodDetail> listFoodDetails = null; // danh sách food detail
		List<orderDetail> listorderDetail = orderDetailService.listOrderbyIdorder(orders.getId()); // lấy order detail
																									// by ordersId
		List<foodOrderRequest> listFoodOrderRequests = new ArrayList<>(); // danh sách food request ra ngoài

		// duyệt qua orderdetail
		for (orderDetail orderdetail : listorderDetail) {
			// lấy ra food
			Food food = foodService.detailFood(orderdetail.getFood().getId());
			foodOrderRequest foodOrderRequest = new foodOrderRequest();
			foodOrderRequest.setFood(food.getName());
			foodOrderRequest.setQuantity(orderdetail.getQuatity());
			listFoodOrderRequests.add(foodOrderRequest);
			// tương ứng với 1 orderdetail quantity sẽ nhân với price của food tương ứng
			total = total + (orderdetail.getQuatity() * food.getPrice());

			listFoodDetails = foodDetailService.listFoodDetail(orderdetail.getFood().getId());
			// duyệt danh sách chi tiết của food
			for (foodDetail foodDetail : listFoodDetails) {
				// i là số lượng nguyên liệu đã dùng quantity của chi tiết food * với số lượng
				// order món đó
				float i = foodDetail.getQuantity() * orderdetail.getQuatity();
				String branchId = food.getBranch() != null ? food.getBranch().getId() : "";
				// lấy ra material tương ứng
				Material material = materialService.detailMaterial(foodDetail.getMaterialCode(),
						food.getRestaurant().getId(), branchId);
				// cập nhật lại số lượng
				float j = material.getQuantity() - i;
				if (j <= material.getStockEnd()) {
					foodService.changeStatusFood(food.getId(), 0);
				}
				material.setQuantity(j);
				materialService.updateMaterial(material);
			}
		}
		orders.setTotalAmount(total);
		orders.setStatus(1);
		orderService.updateOrder(orders);
		tableService.changeStatusById(tableId, 0);
		orderRequest.setEmployeeId(employeeId);
		orderRequest.setTableId(Integer.toString(tableId));
		orderRequest.setOrderId(orders.getId());
		orderRequest.setFoodQuantity(listFoodOrderRequests);
		orderRequest.setStatus(orders.getStatus());
		orderRequest.setDescription(orders.getDescription());
		baseResponse.setStatus(1);
		baseResponse.setMessage("Số tiền cần thanh toán là" + total);
		baseResponse.setData(orderRequest);
		orderRequest.setCreateAt(orders.getCreatedAt());
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<Object> updateOrder(@RequestBody OrderRequest orderRequest) {
		String message = null;
		Orders order = orderService.detailOrders(orderRequest.getEmployeeId(),
				Integer.parseInt(orderRequest.getTableId()), 0);
		if (order.getStatus() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("order này đã thanh toán, không thể cập nhật");
		}
		// update cho order
		order.setDescription(orderRequest.getDescription());
		message = orderService.updateOrder(order) ? "Cập nhật thông tin thành công" : "Không thành công";
		// update orderdetail
		List<orderDetail> listOrderDetails = orderDetailService.listOrderbyIdorder(order.getId());
		List<foodOrderRequest> listFoodOrderRequests = orderRequest.getFoodQuantity();

		List<Integer> listFoodIdOrder = new ArrayList<>();
		List<Integer> listFoodIdOrder1 = new ArrayList<>();

		HashMap<Integer, Integer> listRequest = new HashMap<>();
		List<Integer> listFoodReq = new ArrayList<>();
		for (orderDetail orderDetail : listOrderDetails) {
			listFoodIdOrder.add(orderDetail.getFood().getId());
			listFoodIdOrder1.add(orderDetail.getFood().getId());
		}

		for (foodOrderRequest foodOrderRequest : listFoodOrderRequests) {
			listFoodReq.add(Integer.parseInt(foodOrderRequest.getFood()));
			listRequest.put(Integer.parseInt(foodOrderRequest.getFood()), foodOrderRequest.getQuantity());
		}

		listFoodIdOrder.removeAll(listFoodReq);
		listFoodReq.removeAll(listFoodIdOrder1);

		for (Integer foodid : listFoodIdOrder) {
			listFoodIdOrder1.remove(foodid);
		}

		for (Integer foodid : listFoodReq) {
			orderDetail orderDetail = new orderDetail();
			Food food = foodService.detailFood(foodid);
			if (food.getStatus() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Mon an nay hien da het");
			}
			orderDetail.setFood(food);
			orderDetail.setOrder(order);
			orderDetail.setQuatity(listRequest.get(foodid));
			orderDetailService.createOrderDetail(orderDetail);
		}

		for (Integer foodIdOrder1 : listFoodIdOrder1) {
			Food food = foodService.detailFood(foodIdOrder1);
			if (food.getStatus() == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Mon an nay hien da het");
			}
			orderDetail orderDetail = orderDetailService.detailOrder(order.getId(), foodIdOrder1);
			orderDetail.setFood(food);
			orderDetail.setOrder(order);
			orderDetail.setQuatity(listRequest.get(foodIdOrder1));
			orderDetailService.updateOrderDetail(orderDetail);
		}

		for (Integer foodid : listFoodIdOrder) {
			orderDetailService.deleteOrderDetail(order.getId(), foodid);
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
