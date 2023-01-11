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

	private CheckService checkService;
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
			return "Số điện thoại này đã được sử dụng";
		} else {
			Branch branch = new Branch();
			branch.setId(branchRequest.getPhone().trim());
			branch.setRestaurant(restaurant);
			branch.setName(branchRequest.getName().replaceAll("//s+", " ").trim());
			branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
			branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
			branch.setPhone(branchRequest.getPhone().trim());
			branch.setStatus(1);
			boolean successful = branchRepository.createBranch(branch);
			return successful ? success : "No success";
		}
	}

	@Override
	public BranchRequest detailBranch(String branchId) {
		BranchRequest branchRequest = new BranchRequest();
		Branch branch = branchRepository.detailBranch(branchId);
		if (branch != null) {
			branchRequest.setBranchId(branch.getId());
			branchRequest.setRestaurantId(branch.getRestaurant().getId());
			branchRequest.setName(branch.getName());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStreet(branch.getStreet());
			branchRequest.setStatus(branch.getStatus());
		}
		return branchRequest;

	}

	@Override
	public String updateBranch(String branchId, BranchRequest branchRequest) {
		String message = checkInfor(branchRequest);
		Branch branch = branchRepository.detailBranch(branchId);
		if (!message.equals(success)) {
			return message;
		} else if (restaurantRepository.getRestaurantbyPhone(branchRequest.getPhone()) != null
				|| branchRepository.getDetailByPhone(branchRequest.getPhone()) != null
						&& !branch.getPhone().equalsIgnoreCase(branchRequest.getPhone())) {
			return "Số điện thoại này đã được sử dụng";
		} else {
			branch.setName(branchRequest.getName().replace("//s+", " ").trim());
			branch.setPhone(branchRequest.getPhone().trim());
			branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
			branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
			boolean successful = branchRepository.updateBranch(branch);
			return successful ? success : "No success";
		}
	}

	@Override
	public String changeStatusBranch(String branchId) {
		Branch branch = branchRepository.detailBranch(branchId);
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
	public List<BranchRequest> listBranchByRestaurantId(String restaurantId) {
		List<BranchRequest> branchRequests = new ArrayList<>();
		List<Branch> branchs = branchRepository.listBranchByRestaurantId(restaurantId);
		for (Branch branch : branchs) {
			BranchRequest branchRequest = new BranchRequest();
			branchRequest.setRestaurantId(branch.getRestaurant().getId());
			branchRequest.setBranchId(branch.getId());
			branchRequest.setName(branch.getName());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setStreet(branch.getStreet());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStatus(branch.getStatus());
			branchRequests.add(branchRequest);
		}
		return branchRequests;
	}

	public String checkInfor(BranchRequest branchRequest) {
		if (!checkService.checkName(branchRequest.getName())) {
			return "Tên không hợp lệ";
		} else if (!checkService.checkPhone(branchRequest.getPhone())) {
			return "Số điện thoại không hợp lệ";
		}
		return success;
	}
}
