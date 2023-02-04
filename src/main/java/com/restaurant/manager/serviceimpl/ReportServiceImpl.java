package com.restaurant.manager.serviceimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Food;
import com.restaurant.manager.model.FoodDetail;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.OrderDetail;
import com.restaurant.manager.model.Orders;
import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.FoodDetailRepository;
import com.restaurant.manager.repository.FoodRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.OrderDetailRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.WarehouseDetailRepository;
import com.restaurant.manager.repository.WarehouseRepository;
import com.restaurant.manager.request.ReportRequest;
import com.restaurant.manager.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	WarehouseDetailRepository warehouseDetailRepository;
	@Autowired
	FoodDetailRepository foodDetailRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	FoodRepository foodRepository;

	public Date date(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ReportRequest revenue(int employeeId, String fromDate, String toDate) {
		ReportRequest reportRequest = new ReportRequest();
		Employee employee = employeeRepository.detailEmployee(employeeId);
		List<Orders> orders = orderRepository.listOrder(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), 1);
		orders.stream().forEach(t -> {
			if (t.getCreatedAt().getYear() == date(fromDate).getYear()) {
				reportRequest.setProtfit(reportRequest.getProtfit() + t.getTotalAmount());
			}
		});
		List<Warehouse> warehouses = warehouseRepository.listWarehouse(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId());
		for (Warehouse warehouse : warehouses) {
			List<WarehouseDetail> warehouseDetails = warehouseDetailRepository.listWarehouseDetail(warehouse.getId());
			warehouseDetails.stream().forEach(t -> {
				if (t.getCreateAt().getYear() == date(fromDate).getYear()) {
					reportRequest.setCost(reportRequest.getCost() + (t.getCost() * t.getQuantity()));
				}
			});
		}
		reportRequest.setTurnover(reportRequest.getCost() - reportRequest.getProtfit());
		return reportRequest;
	}

	@Override
	public ReportRequest food(int employeeId, int foodId, String fromDate, String toDate) {
		ReportRequest reportRequest = new ReportRequest();
		Employee employee = employeeRepository.detailEmployee(employeeId);
		List<Orders> orders = orderRepository.listOrder(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), 1);
		List<OrderDetail> orderDetails = null;
		for (Orders order : orders) {

			orderDetails = orderDetailRepository.listOrderDetails(order.getId());
			orderDetails.stream().forEach(t -> {
				if (t.getFood().getId() == foodId) {
					reportRequest.setTurnover(reportRequest.getTurnover() + t.getQuatity());
					reportRequest.setCost(t.getFood().getPrice());
					reportRequest.setProtfit((float) t.getQuatity() * t.getFood().getPrice());
					List<FoodDetail> foodDetails = foodDetailRepository.listFoodDetail(foodId);
					foodDetails.stream().forEach(l -> reportRequest.setProtfit(reportRequest.getProtfit()
							- (l.getQuantity() * l.getMaterial().getCost() * t.getQuatity())));
				}
			});
		}
		return reportRequest;
	}

	@Override
	public ReportRequest warehouse(int employeeId, String fromDate, String toDate) {
		ReportRequest reportRequest = new ReportRequest();
		Employee employee = employeeRepository.detailEmployee(employeeId);
		List<Warehouse> warehouses = warehouseRepository.listWarehouse(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId());
		warehouses.stream().forEach(t -> {
			List<WarehouseDetail> warehouseDetails = new ArrayList<>();
			warehouseDetails = warehouseDetailRepository.listWarehouseDetail(t.getId());
			warehouseDetails.stream().forEach(d -> {
				reportRequest.setTurnover(reportRequest.getTurnover() + d.getQuantity());
				reportRequest.setCost(reportRequest.getCost() + d.getCost());
				reportRequest.setProtfit(reportRequest.getProtfit() + (d.getCost() * d.getQuantity()));
			});
		});
		return null;
	}

	@Override
	public ReportRequest material(int employeeId, int materialId) {
		ReportRequest reportRequest = new ReportRequest();
		Employee employee = employeeRepository.detailEmployee(employeeId);
		Material material = materialRepository.detailMaterial(materialId);
		reportRequest.setCost(material.getCost());
		reportRequest.setProtfit(material.getQuantity());
		List<Orders> orders = orderRepository.listOrder(employee.getRestaurant().getId(),
				employee.getBranch() == null ? 0 : employee.getBranch().getId(), 1);
		orders.stream().forEach(t -> {
			List<OrderDetail> orderDetails = orderDetailRepository.listOrderDetails(t.getId());
			orderDetails.stream().forEach(d -> {
				FoodDetail foodDetail = foodDetailRepository.detailFood(d.getFood().getId(), materialId);
				if (foodDetail != null) {
					
				} else {
					reportRequest.setTurnover(reportRequest.getTurnover());
				}
			});
		});
		return null;
	}
}
