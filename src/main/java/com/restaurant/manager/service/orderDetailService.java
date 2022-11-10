package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.orderDetail;

public interface orderDetailService {
	public boolean createOrderDetail(orderDetail orderDetail);

	public orderDetail detailOrder(int orderId, int foodId);

	public boolean updateOrderDetail(orderDetail orderDetail);
	
	public boolean deleteOrderDetail(int orderId, int foodId);
	
	public List<orderDetail> listOrderbyIdorder(int orderId);
}
