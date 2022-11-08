package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Orders;

public interface OrderService {
	public boolean createOrder(Orders order);

	public Integer getStatusByTableId(int tableId);
	
	public Orders detailOrder(int tableId);

	public boolean updateOrder(Orders order);

	public List<Orders> listOrderByEmployeeId(String employeeId);

	public boolean changeStatus(int tableId, int status);

	public List<Orders> listOrderONorOFF(String employeeId, int status);
}
