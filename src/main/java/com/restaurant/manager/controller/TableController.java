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
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.request.TableRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.RestaurantService;
import com.restaurant.manager.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {

	@Autowired
	TableService tableService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	BranchService branchService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	CheckService checkService;

	@PostMapping("/create")
	ResponseEntity<String> createTable(@Valid @RequestBody TableRequest tableRequest) {
		String message;
		Tables table = new Tables();
		List<Tables> listTable = null;
		Restaurants restaurants = restaurantService.detailRestaurant(tableRequest.getRestaurantId());
		if (restaurants == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng không tồn tại");
		}
		Branch branch = null;
		if (tableRequest.getBranchId() != null) {
			branch = branchService.detailBranch(tableRequest.getBranchId());
			if (branch == null || !branch.getRestaurant().getId().equals(restaurants.getId())) {
				return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh không tồn tại");
			} else {
				branch = branchService.detailBranch(tableRequest.getBranchId());
			}
		}
		String branchId = branch != null ? branch.getId() : "";
		listTable = tableService.listTableByBranchIdandRestaurantId(restaurants.getId(), branchId);
		for (Tables tables : listTable) {
			if (tables.getName().equals(tableRequest.getName().toUpperCase())) {
				return ResponseEntity.status(HttpStatus.OK).body("Bàn này đã có");
			}
		}
		table.setRestaurant(restaurants);
		table.setBranch(branch);
		table.setName(tableRequest.getName().replaceAll("//s+", " ").trim());
		table.setTotalSlot(tableRequest.getTotalSlot());
		table.setDescription(tableRequest.getDescription().replaceAll("//s+", " ").trim());
		table.setStatus(0);
		message = tableService.createTable(table) ? "Tạo bàn mới thành công" : "";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<Object> detailTable(@Valid @RequestParam(name = "id") int id) {
		Tables table = tableService.detailTable(id);
		TableRequest tableRequest = new TableRequest();
		if (table == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Không có dữ liệu");
		} else {
			tableRequest.setTableId(table.getId());
			tableRequest.setRestaurantId(table.getRestaurant().getId());
			String branchId = table.getBranch() != null ? table.getBranch().getId() : null;
			tableRequest.setBranchId(branchId);
			tableRequest.setName(table.getName());
			tableRequest.setTotalSlot(table.getTotalSlot());
			tableRequest.setDescription(table.getDescription());
			tableRequest.setStatus(table.getStatus());
		}
		return ResponseEntity.status(HttpStatus.OK).body(tableRequest);
	}

	@GetMapping("/list")
	ResponseEntity<Object> listTable(@Valid @RequestParam(name = "employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		List<TableRequest> listTableRequest = new ArrayList<>();
		List<Tables> listTable = null;
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		listTable = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(), branchId);
		for (Tables table : listTable) {
			TableRequest tableRequest = new TableRequest();
			tableRequest.setTableId(table.getId());
			tableRequest.setRestaurantId(table.getRestaurant().getId());
			tableRequest.setBranchId(branchId);
			tableRequest.setName(table.getName());
			tableRequest.setTotalSlot(table.getTotalSlot());
			tableRequest.setDescription(table.getDescription());
			tableRequest.setStatus(table.getStatus());
			listTableRequest.add(tableRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listTableRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateTable(@Valid @RequestParam(name = "id") int id,
			@Valid @RequestBody TableRequest tableRequest) {
		String message = null;
		Tables table = tableService.detailTable(id);
		if (table == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Bàn này chưa có");
		}
		String branchId = table.getBranch() != null ? table.getBranch().getId() : "";
		List<Tables> listTable = tableService.listTableByBranchIdandRestaurantId(table.getRestaurant().getId(),
				branchId);
		for (Tables tables : listTable) {
			if (tables.getName().equals(tableRequest.getName().toUpperCase())
					&& !table.getName().equals(tableRequest.getName())) {
				return ResponseEntity.status(HttpStatus.OK).body("Bàn này đã có");
			}
		}
		table.setName(tableRequest.getName().toUpperCase());
		table.setTotalSlot(tableRequest.getTotalSlot());
		table.setDescription(tableRequest.getDescription().replaceAll("\\s+", " ").trim());
		message = tableService.updateTable(table) ? "Cập nhật thông tin thành công" : "";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusTable(@Valid @RequestParam(name = "id") int id) {
		String message;
		Tables table = tableService.detailTable(id);
		if (table == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Bàn này chưa có");
		}
		String branchId = table.getBranch() != null ? table.getBranch().getId() : null;
		if (restaurantService.getStatusById(table.getRestaurant().getId()) == 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng đang ngưng hoạt động");
		} else if (branchId != null) {
			if (branchService.getStatusbyId(branchId) == 0) {
				return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh đang ngưng hoạt động");
			}
		}
		int status = table.getStatus() == 1 ? 0 : 1;
		if (status == 0) {
			message = tableService.changeStatusById(id, status) ? "Đã sẳn sàng phục vụ" : "Không thành công";
		} else {
			message = tableService.changeStatusById(id, status) ? "Bàn này đã được sử dụng" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list-by-status")
	ResponseEntity<Object> listTablebyStatus(@RequestParam("employeeId") String employeeId,
			@RequestParam("status") int status) {
		List<TableRequest> listTableRequest = new ArrayList<>();
		Employee employee = employeeService.detailEmployee(employeeId);
		String branchId = employee.getBranch() != null ? employee.getBranch().getId() : "";
		List<Tables> listTablebyStatus = tableService
				.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(), branchId);
		for (Tables table : listTablebyStatus) {
			if (status == table.getStatus()) {
				TableRequest tableRequest = new TableRequest();
				tableRequest.setRestaurantId(table.getRestaurant().getId());
				tableRequest.setBranchId(branchId);
				tableRequest.setTableId(table.getId());
				tableRequest.setName(table.getName());
				tableRequest.setDescription(table.getDescription());
				tableRequest.setTotalSlot(table.getTotalSlot());
				tableRequest.setStatus(table.getStatus());
				listTableRequest.add(tableRequest);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listTableRequest);
	}
}
