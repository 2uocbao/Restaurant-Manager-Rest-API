package com.restaurant.manager.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Restaurant;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.request.RestaurantRequest;
import com.restaurant.manager.service.CheckService;
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

	private CheckService checkService = new CheckService();
	private String success = "success";

	@Override
	public String createRestaurant(RestaurantRequest restaurantRequest) {
		String message = checkInfor(restaurantRequest);
		if (restaurantRepository.getRestaurantbyEmail(restaurantRequest.getEmail()) != null) {
			return "Email already in use";
		} else if (restaurantRepository.getRestaurantbyPhone(restaurantRequest.getPhone()) != null) {
			return "Phone number already in use";
		} else if (message.equals(success)) {
			Restaurant restaurant = new Restaurant();
			restaurant.setName(restaurantRequest.getName().replaceAll("\\s+", " ").trim());
			restaurant.setEmail(restaurantRequest.getEmail().trim());
			restaurant.setPhone(restaurantRequest.getPhone().trim());
			restaurant.setInfo(restaurantRequest.getInfo().replaceAll("\\s+", " ").trim());
			restaurant.setLogo(restaurantRequest.getLogo());
			restaurant.setAddress(restaurantRequest.getAddress().replaceAll("\\s+", " ").trim());
			restaurant.setStatus(1);
			boolean successful = restaurantRepository.createRestaurant(restaurant);
			return successful ? success : "No success";
		}
		return message;
	}

	@Override
	public String updateRestaurant(int restaurantId, RestaurantRequest restaurantRequest) {
		String message = checkInfor(restaurantRequest);
		Restaurant restaurant = restaurantRepository.detailRestaurant(restaurantId);
		if (!message.equals(success)) {
			return message;
		} else if (restaurantRepository.getRestaurantbyEmail(restaurantRequest.getEmail()) != null
				&& !restaurant.getEmail().equals(restaurantRequest.getEmail())) {
			return "Email already in use";
		} else if (restaurantRepository.getRestaurantbyPhone(restaurantRequest.getPhone()) != null
				&& !restaurant.getPhone().equalsIgnoreCase(restaurantRequest.getPhone())) {
			return "Phone number already in use";
		} else {
			restaurant.setName(restaurantRequest.getName().replaceAll("\\s+", " ").trim());
			restaurant.setEmail(restaurantRequest.getEmail().trim());
			restaurant.setPhone(restaurantRequest.getPhone().trim());
			restaurant.setInfo(restaurantRequest.getInfo().replaceAll("\\s\\s+", " ").trim());
			restaurant.setLogo(restaurantRequest.getLogo());
			restaurant.setAddress(restaurantRequest.getAddress().replaceAll("\\s\\s+", " ").trim());
			boolean successful = restaurantRepository.updateRestaurant(restaurant);
			return successful ? success : "No success";
		}
	}

	@Override
	public RestaurantRequest detailRestaurant(int restaurantId) {
		RestaurantRequest restaurantRequest = new RestaurantRequest();
		Restaurant restaurant = restaurantRepository.detailRestaurant(restaurantId);
		if (restaurant != null) {
			restaurantRequest.setRestaurantId(restaurant.getId());
			restaurantRequest.setName(restaurant.getName());
			restaurantRequest.setEmail(restaurant.getEmail());
			restaurantRequest.setPhone(restaurant.getPhone());
			restaurantRequest.setInfo(restaurant.getInfo());
			restaurantRequest.setLogo(restaurant.getLogo());
			restaurantRequest.setAddress(restaurant.getAddress());
			restaurantRequest.setStatus(restaurant.getStatus());
		} else {
			return null;
		}
		return restaurantRequest;
	}

	@Override
	public String changeStatusRestaurant(int restaurantId) {
		Restaurant restaurant = restaurantRepository.detailRestaurant(restaurantId);
		if (restaurant.getStatus() == 1) {
			branchRepository.changeStatusbyRestaurantId(restaurant.getId(), 0);
			employeeRepository.changeStatusEmployeeByRestaurantId(restaurant.getId(), 0);
			tableRepository.changeStatusTableByRestaurantId(restaurant.getId(), 0);
			restaurantRepository.changeStatusRestaurant(restaurant.getId(), 0);
			return "Inactive";
		}
		restaurantRepository.changeStatusRestaurant(restaurant.getId(), 1);
		return "Active";
	}

	public String checkInfor(RestaurantRequest restaurantRequest) {
		if (!checkService.isValidEmail(restaurantRequest.getEmail())) {
			return "Invalid email";
		} else if (!checkService.checkPhone(restaurantRequest.getPhone())) {
			return "Invalid phone number";
		} else if (!checkService.checkName(restaurantRequest.getName())) {
			return "Invalid name";
		}
		return success;
	}
}
