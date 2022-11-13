package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.request.EmployeeRequest;
import com.restaurant.manager.request.LoginRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.ImageService;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	BranchService branchService;
	@Autowired
	ImageService imageService;
	@Autowired
	CheckService checkService;

	@PostMapping("/create")
	ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
		String message = null;
		Branch branch = branchService.detailBranch(employeeRequest.getBranchId());
		Restaurants restaurant = restaurantService.detailRestaurant(employeeRequest.getRestaurantId());
		Employee employee = new Employee();
		if (branch == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh không tồn tại");
		} else {
			employee.setBranch(branch);
		}
		if (!checkService.checkName(employeeRequest.getFirstName())
				|| !checkService.checkName(employeeRequest.getLastName())
				|| !checkService.checkName(employeeRequest.getFullName())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body("Tên đã nhập không hợp lệ, vui lòng nhập đúng tên của bạn");
		} else if (!checkService.checkPhone(employeeRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không đúng, số điện thoại gồm 10 số");
		} else if (!checkService.isValidEmail(employeeRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.OK).body("Email không đúng, vui lòng nhập lại");
		} else if (employeeService.getEmployeeByEmail(employeeRequest.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Email này đã được sử dụng");
		} else if (employeeService.getEmployeeByPhone(employeeRequest.getPhone()) != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại đã được sử dụng");
		} else {
			employee.setId(employeeRequest.getPhone().trim());
			employee.setRestaurant(restaurant);
			employee.setBranch(branch);
			employee.setFirstName(employeeRequest.getFirstName().replaceAll("\\s+", " ").trim());
			employee.setLastName(employeeRequest.getLastName().replaceAll("\\s+", " ").trim());
			employee.setFullName(employeeRequest.getFullName().replaceAll("\\s+", " ").trim());
			employee.setGender(employeeRequest.getGender().trim());
			employee.setDateOfBirth(employeeRequest.getDateOfbirth());
			employee.setEmail(employeeRequest.getEmail().trim());
			employee.setPhone(employeeRequest.getPhone().trim());
			employee.setRole(employeeRequest.getRole());
			employee.setCity(employeeRequest.getCity().replaceAll("\\s+", " ").trim());
			employee.setDistrict(employeeRequest.getDistrict().replaceAll("\\s+", " ").trim());
			employee.setAddress(employeeRequest.getAddress().replaceAll("\\s+", " ").trim());
			employee.setPassword(employeeRequest.getPassword());
			employee.setStatus(1);
			message = employeeService.createEmployee(employee) ? "Tạo nhân viên mới thành công" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailEmployee(@RequestParam("id") String id) {
		EmployeeRequest employeeRequest = new EmployeeRequest();
		Employee employee = employeeService.detailEmployee(id);
		if (employee == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không tìm thấy nhân viên");
		}
		employeeRequest.setEmployeeId(employee.getId());
		employeeRequest.setRestaurantId(employee.getRestaurant().getId());
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : null;
		employeeRequest.setBranchId(branchId);
		employeeRequest.setFirstName(employee.getFirstName());
		employeeRequest.setLastName(employee.getLastName());
		employeeRequest.setFullName(employee.getFullName());
		employeeRequest.setGender(employee.getGender());
		employeeRequest.setDateOfbirth(employee.getDateOfBirth());
		employeeRequest.setEmail(employee.getEmail());
		employeeRequest.setPhone(employee.getPhone());
		employeeRequest.setRole(employee.getRole());
		employeeRequest.setCity(employee.getCity());
		employeeRequest.setDistrict(employee.getDistrict());
		employeeRequest.setAddress(employee.getAddress());
		return ResponseEntity.status(HttpStatus.OK).body(employeeRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateEmployee(@RequestBody EmployeeRequest employeeRequest, @RequestParam("id") String id) {
		String message;
		Employee employee = employeeService.detailEmployee(id);
		if (employee == null) {
			return ResponseEntity.badRequest().body("Không có dữ liệu về nhân viên này");
		}
		if (!checkService.checkName(employeeRequest.getFirstName())
				|| !checkService.checkName(employeeRequest.getLastName())
				|| !checkService.checkName(employeeRequest.getFullName())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body("Tên đã nhập không hợp lệ, vui lòng nhập đúng tên của bạn");
		} else if (!checkService.checkPhone(employeeRequest.getPhone())) {
			return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại không đúng, số điện thoại gồm 10 số");
		} else if (!checkService.isValidEmail(employeeRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.OK).body("Email không đúng, vui lòng nhập lại");
		}
		if (employeeService.getEmployeeByEmail(employeeRequest.getEmail()) != null) {
			if (!employee.getEmail().equals(employeeRequest.getEmail()))
				return ResponseEntity.status(HttpStatus.OK).body("Email này đã được sử dụng");
		}
		if (employeeService.getEmployeeByPhone(employeeRequest.getPhone()) != null) {
			if (!employee.getPhone().equals(employeeRequest.getPhone()))
				return ResponseEntity.status(HttpStatus.OK).body("Số điện thoại đã được sử dụng");
		}
		employee.setFirstName(employeeRequest.getFirstName().replaceAll("\\s+", " ").trim());
		employee.setLastName(employeeRequest.getLastName().replaceAll("\\s+", " ").trim());
		employee.setFullName(employeeRequest.getFullName().replaceAll("\\s+", " ").trim());
		employee.setGender(employeeRequest.getGender().trim());
		employee.setDateOfBirth(employeeRequest.getDateOfbirth());
		employee.setEmail(employeeRequest.getEmail().trim());
		employee.setPhone(employeeRequest.getPhone().trim());
		employee.setRole(employeeRequest.getRole());
		employee.setCity(employeeRequest.getCity().replaceAll("\\s+", " ").trim());
		employee.setDistrict(employeeRequest.getDistrict().replaceAll("\\s+", " ").trim());
		employee.setAddress(employeeRequest.getAddress().replaceAll("\\s+", " ").trim());
		message = employeeService.updateEmployee(employee) ? "Cập nhật thông tin thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/delete")
	ResponseEntity<String> deleteEmployee(@RequestParam("id") String id) {
		String message;
		message = employeeService.deleteEmployee(id) ? "Thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list")
	ResponseEntity<?> listEmployeeFromBranch(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		List<EmployeeRequest> listEmployeeShow = new ArrayList<>();
		List<Employee> listEmployeeDB = null;
		if (branchId == "") {
			listEmployeeDB = employeeService.listEmpoyeeByResIdOrBranId(restaurantId, "");
		} else {
			listEmployeeDB = employeeService.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
		}
		for (Employee employee : listEmployeeDB) {
			EmployeeRequest employeeRequest = new EmployeeRequest();
			employeeRequest.setEmployeeId(employee.getId());
			employeeRequest.setRestaurantId(employee.getRestaurant().getId());
			employeeRequest.setBranchId(employee.getBranch().getId());
			employeeRequest.setFirstName(employee.getFirstName());
			employeeRequest.setLastName(employee.getLastName());
			employeeRequest.setFullName(employee.getFullName());
			employeeRequest.setGender(employee.getGender());
			employeeRequest.setDateOfbirth(employee.getDateOfBirth());
			employeeRequest.setEmail(employee.getEmail());
			employeeRequest.setPhone(employee.getPhone());
			employeeRequest.setRole(employee.getRole());
			employeeRequest.setCity(employee.getCity());
			employeeRequest.setDistrict(employee.getDistrict());
			employeeRequest.setAddress(employee.getAddress());
			listEmployeeShow.add(employeeRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listEmployeeShow);
	}

	@PostMapping("/change-password")
	ResponseEntity<String> changepasswordEmployee(@RequestParam("id") String id,
			@RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword) {
		String message;
		Employee employee = employeeService.detailEmployee(id);
		if (!employeeService.loginEmployee(employee.getPhone(), oldPassword)) {
			return ResponseEntity.status(HttpStatus.OK).body("Mật khẩu cũ không chính xác");
		}
		message = employeeService.changePasswordEmployee(id, newPassword) ? "Thay đổi mật khẩu thành công"
				: "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changestatusEmployee(@RequestParam("id") String id) {
		String message = null;
		Employee employee = employeeService.detailEmployee(id);
		if (employee == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Người dùng không tồn tại");
		}
		if (restaurantService.getStatusById(employee.getRestaurant().getId()) == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Không thể hoạt động, vì nhà hàng đang ngưng hoạt động");
		}
		if (employee.getBranch() != null) {
			if (branchService.getStatusbyId(employee.getBranch().getId()) == 0) {
				return ResponseEntity.status(HttpStatus.OK)
						.body("Không thể hoạt động, vì chi nhánh đang ngưng hoạt động");
			}
		}
		int status = employee.getStatus() == 0 ? 1 : 0;
		if (status == 1) {
			message = employeeService.changeStatusEmployee(id, status) ? "Bạn đang hoạt động" : "Không thành công";
		} else {
			message = employeeService.changeStatusEmployee(id, status) ? "Bạn không hoạt động" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PostMapping("/signin")
	ResponseEntity<String> authenticateEmploy(@RequestBody LoginRequest loginRequest) {
		if (!employeeService.loginEmployee(loginRequest.getUsername(), loginRequest.getPassword())) {
			return ResponseEntity.status(HttpStatus.OK).body("Đăng nhập không thành công");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Đăng nhập thành công");
	}
}
