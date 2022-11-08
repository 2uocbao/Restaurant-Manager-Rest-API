package com.restaurant.manager.controller;

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
	ResponseEntity<String> createBranch(@Valid @RequestParam(name = "restaurantId") String restaurantId,
			@Valid @RequestBody BranchRequest branchRequest) {
		Branch branch = new Branch();
		Restaurants restaurant = restaurantService.detailRestaurant(restaurantId);
		String message;
		if (restaurant == null) {
			return ResponseEntity.badRequest().body("Không có thông tin về nhà hàng này");
		} else if (restaurantService.getStatusById(restaurantId) == 0) {
			return ResponseEntity.badRequest().body("Nhà hàng đang tạm ngưng hoạt động");
		} else if (!checkService.checkName(branchRequest.getName())) {
			return ResponseEntity.badRequest().body("Tên không hợp lệ");
		} else if (!checkService.checkPhone(branchRequest.getPhone())) {
			return ResponseEntity.badRequest().body("Số điện thoại không hợp lệ");
		} else {
			if (branchService.getDetailByPhone(branchRequest.getPhone()) != null) {
				return ResponseEntity.badRequest().body("Số điện thoại này đã được sử dụng");
			} else {
				branch.setId(branchRequest.getPhone().substring(0, 10).trim());
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
	ResponseEntity<?> detailBranch(@Valid @RequestParam(name = "id") String id) {
		BranchRequest branchRequest = new BranchRequest();
		Branch branch = branchService.detailBranch(id);
		if (branch == null) {
			return ResponseEntity.badRequest().body("Nhà hàng không tồn tại");
		} else {
			branchRequest.setName(branch.getName());
			branchRequest.setPhone(branch.getPhone());
			branchRequest.setAddress(branch.getAddress());
			branchRequest.setStreet(branch.getStreet());
		}
		return ResponseEntity.status(HttpStatus.OK).body(branchRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateBranch(@Valid @RequestParam(name = "id") String id,
			@Valid @RequestBody BranchRequest branchRequest) {
		String message;
		Branch branch = branchService.detailBranch(id);
		branch.setPhone("");
		if (!checkService.checkPhone(branchRequest.getPhone())) {
			return ResponseEntity.badRequest().body("Số điện thoại không hợp lệ");
		} else {
			branchService.updateBranch(branch);
			if (branchService.getDetailByPhone(branchRequest.getPhone()) != null) {
				return ResponseEntity.badRequest().body("Số điện thoại này đã được sử dụng");
			} else {
				branch.setName(branchRequest.getName().replace("//s+", " ").trim());
				branch.setPhone(branchRequest.getPhone().trim());
				branch.setAddress(branchRequest.getAddress().replace("//s+", " ").trim());
				branch.setStreet(branchRequest.getStreet().replace("//s+", " ").trim());
				message = branchService.updateBranch(branch) ? "Cập nhật thông tin thành công" : "Không thành công";
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusBranch(@Valid @RequestParam(name = "id") String id) {
		Branch branch = branchService.detailBranch(id);
		String message;
		int statusFromRestaurant = restaurantService.getStatusById(branch.getRestaurant().getId());
		if (statusFromRestaurant == 0) {
			return ResponseEntity.badRequest()
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
}
