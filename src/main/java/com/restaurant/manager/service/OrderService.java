package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.FoodOrder;
import com.restaurant.manager.request.FoodOrderRequest;
import com.restaurant.manager.request.OrderRequest;

public interface OrderService {
	public String createOrder(OrderRequest orderRequest);

	public String updateOrder(OrderRequest orderRequest);

	public OrderRequest detailOrder(int tableId);

	public List<OrderRequest> listOrder(int employeeId, int status);

	public Float payOrder(int tableId);

	public String addFood(int orderId, FoodOrder foodOrder);

	public String upFood(int orderId, FoodOrder foodOrder);
	
	public String changeStatusFoodOrder(int orderId, int foodId, int status);

	public List<FoodOrderRequest> listFoodOrders(int orderId);
}
