package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Orders;

public interface OrderRepository {
	public boolean createOrder(Orders order);

	public Integer getStatusByOrderId(int orderId);
	
	public boolean updateOrder(Orders order);

	public List<Orders> listOrderByEmployeeId(int employeeId);

	public boolean changeStatus(int orderId, int status);
	
	public Orders detailOrders(int tableId, int status);
	
	public List<Orders> listOrder(int restaurantId, int branchId, int status);

}
