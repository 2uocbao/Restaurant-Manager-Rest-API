package com.restaurant.manager.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.restaurant.manager.model.Orders;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.WarehouseRepository;
import com.restaurant.manager.service.ReportService;

public class ReportServiceImpl implements ReportService {

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	WarehouseRepository warehouseRepository;

	@Override
	public Float incomebyDay(String day, String restaurantId, String branchId) {
		Float total = 0f;
		List<Orders> orders = orderRepository.listOrder(restaurantId, branchId == null ? "" : branchId, 1);
		Date date = date(day);
		for (Orders order : orders) {
			if (date.equals(order.getCreatedAt())) {
				total = total + order.getTotalAmount();
			}
		}
		return total;
	}

	@Override
	public Float incomebyMonth(String day, String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float incomebyYear(int year, String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reportOutofStock(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float spendbyDay(String employee) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date date(String day) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
