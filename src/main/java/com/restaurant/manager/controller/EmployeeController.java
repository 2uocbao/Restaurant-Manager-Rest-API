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
import com.restaurant.manager.response.BaseResponse;
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
	ResponseEntity<BaseResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
		BaseResponse baseResponse = new BaseResponse();
		Restaurants restaurant = restaurantService.detailRestaurant(employeeRequest.getRestaurantId());
		Employee employee = new Employee();
		Branch branch = null;
		if (employeeRequest.getBranchId() != null) {
			branch = branchService.detailBranch(employeeRequest.getBranchId());
			if (branch == null || !branch.getRestaurant().getId().equals(restaurant.getId())) {
				baseResponse.setStatus(-1);
				baseResponse.setMessage("Chi nhánh không tồn tại");
				return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
			} else {
				branch = branchService.detailBranch(employeeRequest.getBranchId());
			}
		}
		if (!checkService.checkName(employeeRequest.getFirstName())
				|| !checkService.checkName(employeeRequest.getLastName())
				|| !checkService.checkName(employeeRequest.getFullName())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Tên đã nhập không hợp lệ, vui lòng nhập đúng tên của bạn");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (!checkService.checkPhone(employeeRequest.getPhone())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Số điện thoại không đúng, số điện thoại gồm 10 số");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (!checkService.isValidEmail(employeeRequest.getEmail())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Email không đúng, vui lòng nhập lại");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (employeeService.getEmployeeByEmail(employeeRequest.getEmail()) != null) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Email này đã được sử dụng");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (employeeService.detailEmployee(employeeRequest.getPhone()) != null) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Số điện thoại đã được sử dụng");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
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
			if (employeeService.createEmployee(employee)) {
				baseResponse.setStatus(1);
				baseResponse.setMessage("Tạo nhân viên mới thành công");
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailEmployee(@RequestParam("id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		EmployeeRequest employeeRequest = new EmployeeRequest();
		Employee employee = employeeService.detailEmployee(id);
		employeeRequest.setEmployeeId(employee.getId());
		employeeRequest.setRestaurantId(employee.getRestaurant().getId());
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
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
		employeeRequest.setStatus(employee.getStatus());
		baseResponse.setStatus(1);
		baseResponse.setMessage("Thông tin cá nhân");
		baseResponse.setData(employeeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequest,
			@RequestParam("id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		Employee employee = employeeService.detailEmployee(id);
		if (!checkService.checkName(employeeRequest.getFirstName())
				|| !checkService.checkName(employeeRequest.getLastName())
				|| !checkService.checkName(employeeRequest.getFullName())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Tên đã nhập không hợp lệ, vui lòng nhập đúng tên của bạn");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (!checkService.checkPhone(employeeRequest.getPhone())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Số điện thoại không đúng, số điện thoại gồm 10 số");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		} else if (!checkService.isValidEmail(employeeRequest.getEmail())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Email không đúng, vui lòng nhập lại");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		if (employeeService.getEmployeeByEmail(employeeRequest.getEmail()) != null
				&& !employee.getEmail().equalsIgnoreCase(employeeRequest.getEmail())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Email này đã được sử dụng");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		if (employeeService.getEmployeeByPhone(employeeRequest.getPhone()) != null
				&& !employee.getPhone().equals(employeeRequest.getPhone())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Số điện thoại đã được sử dụng");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
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
		if (employeeService.updateEmployee(employee)) {
			baseResponse.setStatus(1);
			baseResponse.setMessage("Cập nhật thông tin thành công");
			baseResponse.setData(employeeRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@DeleteMapping("/delete")
	ResponseEntity<BaseResponse> deleteEmployee(@RequestParam("id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		if (employeeService.deleteEmployee(id)) {
			baseResponse.setStatus(1);
			baseResponse.setMessage("Xóa thông tin cá nhân thành công");
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list")
	ResponseEntity<BaseResponse> listEmployeeFromBranch(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		BaseResponse baseResponse = new BaseResponse();
		List<EmployeeRequest> listEmployeeShow = new ArrayList<>();
		List<Employee> listEmployeeDB = employeeService.listEmpoyeeByResIdOrBranId(restaurantId, branchId);
		for (Employee employee : listEmployeeDB) {
			EmployeeRequest employeeRequest = new EmployeeRequest();
			employeeRequest.setEmployeeId(employee.getId());
			employeeRequest.setRestaurantId(employee.getRestaurant().getId());
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
			employeeRequest.setStatus(employee.getStatus());
			listEmployeeShow.add(employeeRequest);
		}
		baseResponse.setStatus(1);
		baseResponse.setMessage("Danh sách nhân viên");
		baseResponse.setData(listEmployeeShow);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/change-password")
	ResponseEntity<BaseResponse> changepasswordEmployee(@RequestParam("id") String id,
			@RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword) {
		BaseResponse baseResponse = new BaseResponse();
		Employee employee = employeeService.detailEmployee(id);
		if (!employeeService.loginEmployee(employee.getPhone(), oldPassword)) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Mật khẩu cũ không chính xác");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		employeeService.changePasswordEmployee(id, newPassword);
		baseResponse.setStatus(1);
		baseResponse.setMessage("Thay đổi mật khẩu thành công");
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/change-status")
	ResponseEntity<BaseResponse> changestatusEmployee(@RequestParam("id") String id) {
		BaseResponse baseResponse = new BaseResponse();
		Employee employee = employeeService.detailEmployee(id);
		if (restaurantService.getStatusById(employee.getRestaurant().getId()) == 0) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Không thể hoạt động, vì nhà hàng đang ngưng hoạt động");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		if (employee.getBranch() != null && branchService.getStatusbyId(employee.getBranch().getId()) == 0) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Không thể hoạt động, vì chi nhánh đang ngưng hoạt động");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		int status = employee.getStatus() == 0 ? 1 : 0;
		if (status == 1) {
			employeeService.changeStatusEmployee(id, status);
			baseResponse.setMessage("Bạn đang hoạt động");
		} else {
			employeeService.changeStatusEmployee(id, status);
			baseResponse.setMessage("Bạn đang ngưng hoạt động");
		}
		baseResponse.setStatus(1);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PostMapping("/signin")
	ResponseEntity<BaseResponse> authenticateEmploy(@RequestBody LoginRequest loginRequest) {
		BaseResponse baseResponse = new BaseResponse();
		Employee employee = employeeService.getEmployeeByPhone(loginRequest.getUsername());
		if (employee == null) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Tên đăng nhập không chính xác");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		if (!employeeService.loginEmployee(loginRequest.getUsername(), loginRequest.getPassword())) {
			baseResponse.setStatus(-1);
			baseResponse.setMessage("Mật khẩu không chính xác");
			return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
		}
		EmployeeRequest employeeRequest = new EmployeeRequest();
		employeeRequest.setEmployeeId(employee.getId());
		employeeRequest.setRestaurantId(employee.getRestaurant().getId());
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
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
		baseResponse.setStatus(1);
		baseResponse.setMessage("Đăng nhập thành công");
		baseResponse.setData(employeeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
