package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.FoodDetail;
import com.restaurant.manager.model.OrderDetail;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.OrderDetailRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.request.FoodOrder;
import com.restaurant.manager.request.FoodOrderRequest;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	TableRepository tableRepository;
	@Autowired
	FoodRepository foodRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	FoodDetailRepository foodDetailRepository;
	@Autowired
	MaterialRepository materialRepository;

	private String success = "success";
	private String nosucess = "No success";

	@Override
	public String createOrder(OrderRequest orderRequest) {
		boolean successful = false;
		Employee employee = employeeRepository.detailEmployee(orderRequest.getEmployeeId());
		Tables tables = tableRepository.detailTable(orderRequest.getTableId());
		Orders orders = new Orders();
		orders.setEmployee(employee);
		orders.setTable(tables);
		orders.setDescription(orderRequest.getDescription());
		orders.setStatus(0);
		tableRepository.changeStatusById(tables.getId(), 1);
		successful = orderRepository.createOrder(orders);
		return successful ? success : nosucess;
	}

	@Override
	public String addFood(int orderId, FoodOrder foodOrder) {
		boolean successful = false;
		Orders orders = orderRepository.detailOrders(orderId, 0);
		if (orders == null) {
			return "Order not found";
		}
		OrderDetail orderDetail = new OrderDetail();
		for (FoodOrderRequest foodOrderRequest : foodOrder.getFoodOrderRequests()) {
			Food food = foodRepository.detailFood(foodOrderRequest.getFoodId());
			orderDetail.setFood(food);
			orderDetail.setOrder(orders);
			orderDetail.setQuatity(foodOrderRequest.getQuantity());
			orderDetail.setStatus(0);
			successful = orderDetailRepository.createOrderDetail(orderDetail);
		}
		return successful ? success : nosucess;
	}

	@Override
	public OrderRequest detailOrder(int tableId) {
		Orders orders = orderRepository.detailOrders(tableId, 0);
		return orderRequests(orders);
	}

	@Override
	public String updateOrder(OrderRequest orderRequest) {
		boolean successful = false;
		Orders orders = orderRepository.detailOrders(orderRequest.getTableId(), 0);
		Tables tables = tableRepository.detailTable(orderRequest.getTableId());
		orders.setTable(tables);
		orders.setDescription(orderRequest.getDescription());
		successful = orderRepository.updateOrder(orders);
		return successful ? success : "No success";
	}

	@Override
	public String upFood(int orderId, FoodOrder foodOrder) {
		boolean successful = false;
		List<OrderDetail> orderDetails = orderDetailRepository.listOrderDetails(orderId);
		for (FoodOrderRequest foodOrderRequest : foodOrder.getFoodOrderRequests()) {
			for (OrderDetail orderDetail : orderDetails) {
				if (foodOrderRequest.getFoodId() == orderDetail.getFood().getId()) {
					orderDetail.setQuatity(foodOrderRequest.getQuantity());
					successful = orderDetailRepository.updateOrderDetail(orderDetail);
				}
			}
		}
		return successful ? success : nosucess;
	}

	@Override
	public List<OrderRequest> listOrder(int employeeId, int status) {
		Employee employee = employeeRepository.detailEmployee(employeeId);
		List<Orders> orders = orderRepository.listOrder(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), status);
		List<OrderRequest> orderRequests = new ArrayList<>();
		for (Orders order : orders) {
			orderRequests.add(orderRequests(order));
		}
		return orderRequests;
	}

	@Override
	public List<FoodOrderRequest> listFoodOrders(int orderId) {
		List<OrderDetail> orderDetails = orderDetailRepository.listOrderDetails(orderId);
		List<FoodOrderRequest> foodOrderRequests = new ArrayList<>();
		for (OrderDetail orderdetail : orderDetails) {
			Food food = foodRepository.detailFood(orderdetail.getFood().getId());
			FoodOrderRequest foodOrderRequest = new FoodOrderRequest();
			foodOrderRequest.setFoodId(food.getId());
			foodOrderRequest.setQuantity(orderdetail.getQuatity());
			foodOrderRequest.setPrice(food.getPrice());
			foodOrderRequest.setTotal((float) food.getPrice() * orderdetail.getQuatity());
			foodOrderRequest.setStatus(orderdetail.getStatus());
			foodOrderRequests.add(foodOrderRequest);
		}
		return foodOrderRequests;
	}

	@Override
	public String changeStatusFoodOrder(int orderId, int foodId, int status) {
		String processing = "processing";
		String finished = "finished";
		OrderDetail orderDetail = orderDetailRepository.getorderDetail(orderId, foodId);
		orderDetail.setStatus(status);
		orderDetailRepository.updateOrderDetail(orderDetail);
		if (status == 1) {
			return processing;
		} else {
			return finished;
		}
	}

	@Override
	public Float payOrder(int orderId) {
		float total = 0;
		Orders orders = orderRepository.detailOrders(orderId, 0);
		List<FoodDetail> foodDetails = null; // danh sách food detail
		List<OrderDetail> listorderDetail = orderDetailRepository.listOrderDetails(orders.getId());
		// duyệt qua orderdetail
		for (OrderDetail orderdetail : listorderDetail) {
			if (orderdetail.getStatus() == 2) {
				// tương ứng với 1 orderdetail quantity sẽ nhân với price của food tương ứng
				total = total + (orderdetail.getQuatity() * orderdetail.getFood().getPrice());
				foodDetails = foodDetailRepository.listFoodDetail(orderdetail.getFood().getId());
				// duyệt danh sách chi tiết của food
				if (orderdetail.getStatus() < 3) {
					for (FoodDetail foodDetail : foodDetails) {
						// i = số lượng nguyên liệu cần dùng cho món ăn x số lần gọi món ăn đó
						float i = foodDetail.getQuantity() * orderdetail.getQuatity();
						// cập nhật lại số lượng
						float j = foodDetail.getMaterial().getQuantity() - i;
						if (j <= foodDetail.getMaterial().getStockEnd()) {
							foodRepository.changeStatusFood(foodDetail.getId(), 0);
						}
						foodDetail.getMaterial().setQuantity(j);
						materialRepository.updateMaterial(foodDetail.getMaterial());
					}
				}
			}
			orderdetail.setStatus(3);
			orderDetailRepository.updateOrderDetail(orderdetail);
		}
		orders.setTotalAmount(total);
		orders.setStatus(1);
		orderRepository.updateOrder(orders);
		tableRepository.changeStatusById(orders.getTable().getId(), 0);
		return total;
	}

	public OrderRequest orderRequests(Orders order) {
		OrderRequest orderRequest = new OrderRequest();
		Tables table = tableRepository.detailTable(order.getTable().getId());
		orderRequest.setEmployeeId(order.getEmployee().getId());
		orderRequest.setTableId(table.getId());
		orderRequest.setOrderId(order.getId());
		orderRequest.setDescription(order.getDescription());
		orderRequest.setTotalAmount(order.getTotalAmount());
		orderRequest.setStatus(order.getStatus());
		orderRequest.setCreateAt(order.getCreatedAt());
		return orderRequest;
	}
}
