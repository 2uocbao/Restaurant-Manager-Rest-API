package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.repository.orderDetailRepository;
import com.restaurant.manager.service.orderDetailService;

@Service
public class orderDetailServiceImpl implements orderDetailService {

	@Autowired
	orderDetailRepository orderDetailRepository;

	@Override
	public boolean createOrderDetail(orderDetail orderDetail) {
		return orderDetailRepository.createOrderDetail(orderDetail);
	}

	@Override
	public orderDetail detailOrder(int orderId) {
		return orderDetailRepository.detailOrder(orderId);
	}

	@Override
	public boolean updateOrderDetail(orderDetail orderDetail) {
		return orderDetailRepository.updateOrderDetail(orderDetail);
	}

	@Override
	public List<orderDetail> listOrderbyIdorder(int orderId) {
		return orderDetailRepository.listOrderbyIdorder(orderId);
	}

	@Override
	public boolean deleteOrderDetail(int orderId, int foodId) {
		return orderDetailRepository.deleteOrderDetail(orderId, foodId);
	}
}
