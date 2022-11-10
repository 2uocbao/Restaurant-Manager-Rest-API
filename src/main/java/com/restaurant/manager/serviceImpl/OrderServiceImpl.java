package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Orders;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Override
	public boolean createOrder(Orders order) {
		return orderRepository.createOrder(order);
	}

	@Override
	public Integer getStatusByTableId(int tableId) {
		return orderRepository.getStatusByTableId(tableId);
	}

	@Override
	public boolean updateOrder(Orders order) {
		return orderRepository.updateOrder(order);
	}

	@Override
	public List<Orders> listOrderByEmployeeId(String employeeId) {
		return orderRepository.listOrderByEmployeeId(employeeId);
	}

	@Override
	public boolean changeStatus(int tableId, int status) {
		return orderRepository.changeStatus(tableId, status);
	}

	@Override
	public Orders detailOrder(int tableId) {
		return orderRepository.detailOrder(tableId);
	}

}
