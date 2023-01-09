package com.restaurant.manager.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.repository.orderDetailRepository;
import com.restaurant.manager.request.OrderRequest;
import com.restaurant.manager.request.foodOrderRequest;
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
	orderDetailRepository orderDetailRepository;
	@Autowired
	FoodDetailRepository foodDetailRepository;
	@Autowired
	MaterialRepository materialRepository;

	private String success = "success";

	@Override
	public String createOrder(OrderRequest orderRequest) {
		boolean successful = false;
		Employee employee = employeeRepository.detailEmployee(orderRequest.getEmployeeId());
		Tables tables = tableRepository.detailTable(Integer.parseInt(orderRequest.getTableId()));
		Orders orders = new Orders();
		orders.setRestaurant(employee.getRestaurant());
		orders.setBranch(employee.getBranch() == null ? null : employee.getBranch());
		orders.setEmployee(employee);
		orders.setTable(tables);
		orders.setDescription(orderRequest.getDescription());
		orders.setStatus(0);
		successful = orderRepository.createOrder(orders);
		orderDetail orderDetail = new orderDetail();
		for (foodOrderRequest foodOrderRequest : orderRequest.getFoodQuantity()) {
			Food food = foodRepository.detailFood(Integer.parseInt(foodOrderRequest.getFood()));
			orderDetail.setFood(food);
			orderDetail.setOrder(orders);
			orderDetail.setQuatity(foodOrderRequest.getQuantity());
			orderDetail.setStatus(0);
			successful = orderDetailRepository.createOrderDetail(orderDetail);
		}
		return successful ? success : "No success";
	}

	@Override
	public OrderRequest detailOrder(int tableId) {
		Orders orders = orderRepository.detailOrders(tableId, 0);
		return orderRequests(orders);
	}

	@Override
	public String updateOrder(OrderRequest orderRequest) {
		boolean successful = false;
		Orders orders = orderRepository.detailOrders(Integer.parseInt(orderRequest.getTableId()), 0);
		orders.setDescription(orderRequest.getDescription());

		List<foodOrderRequest> foodRequests = orderRequest.getFoodQuantity();
		List<orderDetail> orderDetails = orderDetailRepository.listOrderbyIdorder(orders.getId());

		List<Integer> foodidDetail = new ArrayList<>();
		Set<Integer> foodidRequest = new HashSet<>();

		for (orderDetail orderDetail : orderDetails) {
			foodidDetail.add(orderDetail.getFood().getId());
			for (foodOrderRequest foodOrderRequest : foodRequests) {
				foodidRequest.add(foodOrderRequest.getFoodId());
				if (orderDetail.getFood().getId() == foodOrderRequest.getFoodId()) {
					orderDetail.setQuatity(orderDetail.getQuatity() + foodOrderRequest.getQuantity());
					successful = orderDetailRepository.updateOrderDetail(orderDetail);
				}
			}
		}

		foodidRequest.removeAll(foodidDetail);
		for (Integer integer : foodidRequest) {
			for (foodOrderRequest foodOrderRequest : foodRequests) {
				if (foodOrderRequest.getFoodId() == integer) {
					Food food = foodRepository.detailFood(integer);
					orderDetail orderDetail = new orderDetail();
					orderDetail.setFood(food);
					orderDetail.setOrder(orders);
					orderDetail.setQuatity(foodOrderRequest.getQuantity());
					successful = orderDetailRepository.createOrderDetail(orderDetail);
				}
			}
		}
		return successful ? success : "No success";
	}

	@Override
	public List<Orders> listOrder(String restaurantId, String branchId, int status) {
		return orderRepository.listOrder(restaurantId, branchId, status);
	}

	@Override
	public Float payOrder(int tableId) {
		float total = 0;
		Orders orders = orderRepository.detailOrders(tableId, 0);
		List<foodDetail> foodDetails = null; // danh sách food detail
		List<orderDetail> listorderDetail = orderDetailRepository.listOrderbyIdorder(orders.getId()); // lấy order
																										// detail
																										// by ordersId
		// duyệt qua orderdetail
		for (orderDetail orderdetail : listorderDetail) {
			// lấy ra food
			Food food = foodRepository.detailFood(orderdetail.getFood().getId());
			// tương ứng với 1 orderdetail quantity sẽ nhân với price của food tương ứng
			total = total + (orderdetail.getQuatity() * food.getPrice());

			foodDetails = foodDetailRepository.listFoodDetail(orderdetail.getFood().getId());
			// duyệt danh sách chi tiết của food
			for (foodDetail foodDetail : foodDetails) {
				// i là số lượng nguyên liệu đã dùng quantity của chi tiết food * với số lượng
				// order món đó
				float i = foodDetail.getQuantity() * orderdetail.getQuatity();
				// lấy ra material tương ứng
				Material material = materialRepository.detailMaterial(foodDetail.getMaterial().getId());
				// cập nhật lại số lượng
				float j = material.getQuantity() - i;
				if (j <= material.getStockEnd()) {
					foodRepository.changeStatusFood(food.getId(), 0);
				}
				material.setQuantity(j);
				materialRepository.updateMaterial(material);
			}
		}
		orders.setTotalAmount(total);
		orders.setStatus(1);
		orderRepository.updateOrder(orders);
		tableRepository.changeStatusById(tableId, 0);
		return total;
	}

	public OrderRequest orderRequests(Orders order) {
		List<orderDetail> listOrderDetails = orderDetailRepository.listOrderbyIdorder(order.getId());
		// list mon an
		// list so luong order
		List<foodOrderRequest> listFoodOrderRequests = new ArrayList<>();
		// lay ra danh sach mon an va so luong chinh xac order cua tung mon
		for (orderDetail orderdetail : listOrderDetails) {
			Food food = foodRepository.detailFood(orderdetail.getFood().getId());
			foodOrderRequest foodOrderRequest = new foodOrderRequest();
			foodOrderRequest.setFoodId(food.getId());
			foodOrderRequest.setFood(food.getName());
			foodOrderRequest.setQuantity(orderdetail.getQuatity());
			foodOrderRequest.setPrice(food.getPrice());
			foodOrderRequest.setTotal((float) food.getPrice() * orderdetail.getQuatity());
			foodOrderRequest.setStatus(orderdetail.getStatus());
			listFoodOrderRequests.add(foodOrderRequest);
		}
		// tao orderrequest, set cac thuoc tinh
		OrderRequest orderRequest = new OrderRequest();
		Tables table = tableRepository.detailTable(order.getTable().getId());
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
