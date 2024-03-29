package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Restaurant;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.request.BranchRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.CheckService;

@Service
public class BranchServiceImpl implements BranchService {
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
	public String createBranch(BranchRequest branchRequest) {
		String message = checkInfor(branchRequest);
		Restaurant restaurant = restaurantRepository.detailRestaurant(branchRequest.getRestaurantId());
		if (!message.equals(success)) {
			return message;
		} else if (restaurant.getStatus() == 0) {
			return "Restaurant Inactive";
		} else if (restaurantRepository.getRestaurantbyPhone(branchRequest.getPhone()) != null
				|| branchRepository.getDetailByPhone(branchRequest.getPhone()) != null) {
			return "Phone number already in use";
		} else {
			Branch branch = new Branch();
			branch.setRestaurant(restaurant);
			branch.setName(branchRequest.getName().replaceAll("//s+", " ").trim());
			branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
			branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
			branch.setPhone(branchRequest.getPhone().trim());
			branch.setStatus(1);
			branch = branchRepository.createBranch(branch);
			return branch != null ? success : "No success";
		}
	}

	@Override
	public BranchRequest detailBranch(int branchId) {
		BranchRequest branchRequest = new BranchRequest();
		Branch branch = branchRepository.detailBranch(branchId);
		if (branch != null) {
			branchRequest.setBranchId(branch.getId());
			branchRequest.setRestaurantId(branch.getRestaurant().getId());
			branchRequest.setName(branch.getName());
			branchRequest.setLogo(branch.getRestaurant().getLogo());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setInfo(branch.getRestaurant().getInfo());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStreet(branch.getStreet());
			branchRequest.setStatus(branch.getStatus());
		} else {
			return null;
		}
		return branchRequest;

	}

	@Override
	public String updateBranch(int branchId, BranchRequest branchRequest) {
		String message = checkInfor(branchRequest);
		Branch branch = branchRepository.detailBranch(branchId);
		if (!message.equals(success)) {
			return message;
		} else if (restaurantRepository.getRestaurantbyPhone(branchRequest.getPhone()) != null
				|| branchRepository.getDetailByPhone(branchRequest.getPhone()) != null
						&& !branch.getPhone().equalsIgnoreCase(branchRequest.getPhone())) {
			return "Phone number already in use";
		} else {
			branch.setName(branchRequest.getName().replace("//s+", " ").trim());
			branch.setPhone(branchRequest.getPhone().trim());
			branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
			branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
			branch = branchRepository.updateBranch(branch);
			return branch != null ? success : "No success";
		}
	}

	@Override
	public String changeStatusBranch(int branchId) {
		Branch branch = branchRepository.detailBranch(branchId);
		if (branch.getRestaurant().getStatus() == 0) {
			return "The restaurant is not working";
		}
		if (branch.getStatus() == 1) {
			employeeRepository.changeStatusEmployeeByBranchId(branch.getId(), 0);
			tableRepository.changeStatusTableByBranchId(branch.getId(), 0);
			branchRepository.changeStatusBranch(branch.getId(), 0);
			return "Inactive";
		}
		branchRepository.changeStatusBranch(branch.getId(), 1);
		return "Active";
	}

	@Override
	public List<BranchRequest> listBranchByRestaurantId(int restaurantId) {
		List<BranchRequest> branchRequests = new ArrayList<>();
		List<Branch> branchs = branchRepository.listBranchByRestaurantId(restaurantId);
		for (Branch branch : branchs) {
			BranchRequest branchRequest = new BranchRequest();
			branchRequest.setRestaurantId(branch.getRestaurant().getId());
			branchRequest.setBranchId(branch.getId());
			branchRequest.setName(branch.getName());
			branchRequest.setLogo(branch.getRestaurant().getLogo());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setInfo(branch.getRestaurant().getInfo());
			branchRequest.setStreet(branch.getStreet());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStatus(branch.getStatus());
			branchRequests.add(branchRequest);
		}
		return branchRequests;
	}

	public String checkInfor(BranchRequest branchRequest) {
		if (!checkService.checkName(branchRequest.getName())) {
			return "Invalid name";
		} else if (!checkService.checkPhone(branchRequest.getPhone())) {
			return "Invalid phone number";
		}
		return success;
	}
}
