package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Orders;
import com.restaurant.manager.request.OrderRequest;

public interface OrderService {
	public String createOrder(OrderRequest orderRequest);

	public String updateOrder(OrderRequest orderRequest);

	public OrderRequest detailOrder(int tableId);

	public List<Orders> listOrder(String restaurantId, String branchId, int status);

	public Float payOrder(int tableId);
}
