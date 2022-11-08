package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.orderDetail;

public interface orderDetailService {
	public boolean createOrderDetail(orderDetail orderDetail);

	public orderDetail detailOrder(int orderId);

	public boolean updateOrder(orderDetail orderDetail);
	
	public List<orderDetail> listOrderbyIdorder(int orderId);
}
