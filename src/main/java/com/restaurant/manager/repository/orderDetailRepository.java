package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.orderDetail;

public interface orderDetailRepository {
	public boolean createOrderDetail(orderDetail orderDetail);

	public orderDetail detailOrder(int orderId);

	public boolean updateOrder(orderDetail orderDetail);

	public List<orderDetail> listOrderbyIdorder(int orderId);
}
