package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.request.BranchRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	BranchService branchService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	CheckService checkService;

	@PostMapping("/create")
	ResponseEntity<String> createBranch(@Valid @RequestBody BranchRequest branchRequest) {
		Branch branch = new Branch();
		Restaurants restaurant = restaurantService.detailRestaurant(branchRequest.getRestaurantId());
		String message;
		if (restaurantService.getStatusById(branchRequest.getRestaurantId()) == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng đang tạm ngưng hoạt động");
		} else if (!checkService.checkName(branchRequest.getName())) {
			return ResponseEntity.status(HttpStatus.OK).body("Tên không hợp lệ");
		} else if (!checkService.checkPhone(branchRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không hợp lệ");
		} else {
			if (restaurantService.getRestaurantbyPhone(branchRequest.getPhone()) != null
					|| branchService.getDetailByPhone(branchRequest.getPhone()) != null) {
				return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại này đã được sử dụng");
			} else {
				branch.setId(branchRequest.getPhone().trim());
				branch.setRestaurant(restaurant);
				branch.setName(branchRequest.getName().replaceAll("//s+", " ").trim());
				branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
				branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
				branch.setPhone(branchRequest.getPhone().trim());
				branch.setStatus(1);
				message = branchService.createBranch(branch) ? "Tạo chi nhánh mới thành công" : "Không thành công";
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<Object> detailBranch(@Valid @RequestParam(name = "id") String id) {
		BranchRequest branchRequest = new BranchRequest();
		Branch branch = branchService.detailBranch(id);
		branchRequest.setBranchId(branch.getId());
		branchRequest.setRestaurantId(branch.getRestaurant().getId());
		branchRequest.setName(branch.getName());
		branchRequest.setPhone(branch.getPhone());
		branchRequest.setAddress(branch.getAddress());
		branchRequest.setStreet(branch.getStreet());
		branchRequest.setStatus(branch.getStatus());
		return ResponseEntity.status(HttpStatus.OK).body(branchRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateBranch(@Valid @RequestParam(name = "id") String id,
			@Valid @RequestBody BranchRequest branchRequest) {
		String message;
		Branch branch = branchService.detailBranch(id);
		if (!checkService.checkPhone(branchRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không hợp lệ");
		} else {
			if (restaurantService.getRestaurantbyPhone(branchRequest.getPhone()) != null
					|| branchService.getDetailByPhone(branchRequest.getPhone()) != null
							&& !branch.getPhone().equalsIgnoreCase(branchRequest.getPhone())) {
				return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại này đã được sử dụng");
			}
			branch.setName(branchRequest.getName().replace("//s+", " ").trim());
			branch.setPhone(branchRequest.getPhone().trim());
			branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
			branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
			message = branchService.updateBranch(branch) ? "Cập nhật thông tin thành công" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusBranch(@Valid @RequestParam(name = "id") String id) {
		Branch branch = branchService.detailBranch(id);
		String message;
		int statusFromRestaurant = restaurantService.getStatusById(branch.getRestaurant().getId());
		if (statusFromRestaurant == 0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body("Không thể trở lại hoạt động, vì nhà hàng chính đang tạm ngưng hoạt động");
		} else {
			int statusBranchNow = branch.getStatus() == 0 ? 1 : 0;
			if (statusBranchNow == 1) {
				message = branchService.changeStatusBranch(id, statusBranchNow) ? "Chi nhánh đang hoạt động"
						: "Chi nhánh đang tạm ngưng hoạt động";
			} else {
				message = branchService.changeStatusBranch(id, statusBranchNow) ? "Chi nhánh đang tạm dừng hoạt động"
						: "Chi nhánh đang tạm ngưng hoạt động";
			}

		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list-branch")
	ResponseEntity<Object> listBranchByRestaurantId(@RequestParam("restaurantId") String restaurantId) {
		List<Branch> listBranch = branchService.listBranchByRestaurantId(restaurantId);
		List<BranchRequest> listBranchRequest = new ArrayList<>();
		for (Branch branch : listBranch) {
			BranchRequest branchRequest = new BranchRequest();
			branchRequest.setRestaurantId(branch.getRestaurant().getId());
			branchRequest.setBranchId(branch.getId());
			branchRequest.setName(branch.getName());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setStreet(branch.getStreet());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStatus(branch.getStatus());
			listBranchRequest.add(branchRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listBranchRequest);
	}
}
