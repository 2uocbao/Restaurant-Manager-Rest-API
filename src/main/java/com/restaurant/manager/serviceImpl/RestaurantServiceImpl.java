package com.restaurant.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TableRepository tableRepository;

	@Override
	public boolean createRestaurant(Restaurants restaurant) {
		return restaurantRepository.createRestaurant(restaurant);
	}

	@Override
	public boolean updateRestaurant(Restaurants restaurant) {
		return restaurantRepository.updateRestaurant(restaurant);
	}

	@Override
	public Restaurants detailRestaurant(String id) {
		return restaurantRepository.detailRestaurant(id);
	}

	@Override
	public boolean changeStatusRestaurant(String id, int status) {
		if (status == 0) {
			branchRepository.changeStatusbyRestaurantId(id, status);
			employeeRepository.changeStatusEmployeeByRestaurantId(id, status);
			tableRepository.changeStatusTableByRestaurantId(id, status);
		}
		return restaurantRepository.changeStatusRestaurant(id, status);
	}

	@Override
	public Restaurants getRestaurantbyPhone(String phone) {
		return restaurantRepository.getRestaurantbyPhone(phone);
	}

	@Override
	public Restaurants getRestaurantbyEmail(String email) {
		return restaurantRepository.getRestaurantbyEmail(email);
	}

	@Override
	public int getStatusById(String id) {
		return restaurantRepository.getStatusById(id);
	}
}
