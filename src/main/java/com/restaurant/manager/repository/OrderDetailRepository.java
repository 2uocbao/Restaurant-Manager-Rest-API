package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.OrderDetail;

public interface OrderDetailRepository {
	public boolean createOrderDetail(OrderDetail orderDetail);
	
	public OrderDetail getorderDetail(int orderId, int foodId);

	public boolean updateOrderDetail(OrderDetail orderDetail);
	
	public boolean deleteOrderDetail(int orderId, int foodId);

	public List<OrderDetail> listOrderDetails(int orderId);
}
