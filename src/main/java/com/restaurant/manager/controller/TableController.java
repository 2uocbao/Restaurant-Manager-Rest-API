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

import com.restaurant.manager.model.Employee;
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
	ResponseEntity<String> createTable(@Valid @RequestParam(name = "employeeId") String employeeId,
			@Valid @RequestBody TableRequest tableRequest) {
		String message = null;
		Tables table = new Tables();
		List<Tables> listTable = new ArrayList<>();
		Employee employee = employeeService.detailEmployee(employeeId);
		if (employee.getBranch() == null) {
			listTable = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(), "");
		} else {
			listTable = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		table.setRestaurant(employee.getRestaurant());
		table.setBranch(employee.getBranch());
		table.setName(tableRequest.getName().replaceAll("//s+", " ").trim());
		table.setTotalSlot(tableRequest.getTotalSlot());
		table.setDescription(tableRequest.getDescription().replaceAll("//s+", " ").trim());
		table.setStatus(1);
		for (Tables tables : listTable) {
			if (tables.getName().equals(tableRequest.getName().toUpperCase())) {
				return ResponseEntity.badRequest().body("Bàn này đã có");
			}
		}
		message = tableService.createTable(table) ? "Tạo bàn mới thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailTable(@Valid @RequestParam(name = "id") int id) {
		Tables table = tableService.detailTable(id);
		TableRequest tableRequest = new TableRequest();
		if (table == null) {
			return ResponseEntity.badRequest().body("Bàn này chưa có");
		} else {
			tableRequest.setName(table.getName());
			tableRequest.setTotalSlot(table.getTotalSlot());
			tableRequest.setDescription(table.getDescription());
		}
		return ResponseEntity.status(HttpStatus.OK).body(tableRequest);
	}

	@GetMapping("/list")
	ResponseEntity<?> listTable(@Valid @RequestParam(name = "employeeId") String employeeId) {
		Employee employee = employeeService.detailEmployee(employeeId);
		List<TableRequest> listTableRequest = new ArrayList<>();
		List<Tables> listTable = new ArrayList<>();
		if (employee.getBranch() == null) {
			listTable = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(), "");
		} else {
			listTable = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		if (employee.getBranch() == null) {
			for (Tables table : listTable) {
				if (table.getBranch() == null) {
					TableRequest tableRequest = new TableRequest();
					tableRequest.setName(table.getName());
					tableRequest.setTotalSlot(table.getTotalSlot());
					tableRequest.setDescription(table.getDescription());
					listTableRequest.add(tableRequest);
				}
			}
		} else {
			for (Tables table : listTable) {
				if (table.getBranch() != null) {
					TableRequest tableRequest = new TableRequest();
					tableRequest.setName(table.getName());
					tableRequest.setTotalSlot(table.getTotalSlot());
					tableRequest.setDescription(table.getDescription());
					listTableRequest.add(tableRequest);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listTableRequest);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateTable(@Valid @RequestParam(name = "id") int id,
			@Valid @RequestBody TableRequest tableRequest) {
		String message = null;
		Tables table = tableService.detailTable(id);
		List<Tables> listTable = new ArrayList<>();
		if (table.getBranch() == null) {
			listTable = tableService.listTableByBranchIdandRestaurantId(table.getRestaurant().getId(), "");
		} else {
			listTable = tableService.listTableByBranchIdandRestaurantId(table.getRestaurant().getId(),
					table.getBranch().getId());
		}
		for (Tables tables : listTable) {
			if (tables.getName().equals(tableRequest.getName().toUpperCase())) {
				return ResponseEntity.badRequest().body("Bàn này đã có");
			}
		}
		table.setName(tableRequest.getName().toUpperCase());
		table.setTotalSlot(tableRequest.getTotalSlot());
		table.setDescription(tableRequest.getDescription().replaceAll("\\s+", " ").trim());
		message = tableService.updateTable(table) ? "Cập nhật thông tin thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/change-status")
	ResponseEntity<String> changeStatusTable(@Valid @RequestParam(name = "id") int id) {
		String message = null;
		Tables table = tableService.detailTable(id);
		if (restaurantService.getStatusById(table.getRestaurant().getId()) == 0) {
			return ResponseEntity.badRequest().body("Nhà hàng đang ngưng hoạt động");
		} else if (branchService.getStatusbyId(table.getBranch().getId()) == 0) {
			return ResponseEntity.badRequest().body("Chi nhánh đang ngưng hoạt động");
		} else {
			int status = table.getStatus() == 1 ? 0 : 1;
			if (status == 0) {
				message = tableService.changeStatusById(id, status) ? "Đã sẳn sàng phục vụ" : "Không thành công";
			} else {
				message = tableService.changeStatusById(id, status) ? "Bàn này đã được sử dụng" : "Không thành công";
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/list-by-status")
	ResponseEntity<?> listTablebyStatus(@RequestParam("employeeId") String employeeId,
			@RequestParam("status") int status) {
		List<Tables> listTablebyStatus = new ArrayList<>();
		List<TableRequest> listTableRequest = new ArrayList<>();
		Employee employee = employeeService.detailEmployee(employeeId);
		if (employee.getBranch() == null) {
			listTablebyStatus = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(), "");
		} else {
			listTablebyStatus = tableService.listTableByBranchIdandRestaurantId(employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		for (Tables table : listTablebyStatus) {
			if (status == table.getStatus() && employee.getBranch() == table.getBranch()) {
				TableRequest tableRequest = new TableRequest();
				tableRequest.setName(table.getName());
				tableRequest.setDescription(table.getDescription());
				tableRequest.setTotalSlot(table.getTotalSlot());
				listTableRequest.add(tableRequest);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(listTableRequest);
	}
}
