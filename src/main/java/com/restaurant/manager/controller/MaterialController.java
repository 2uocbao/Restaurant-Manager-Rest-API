package com.restaurant.manager.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.restaurant.manager.model.Material;
import com.restaurant.manager.request.MaterialRequest;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.EmployeeService;
import com.restaurant.manager.service.MaterialService;

@RestController
@RequestMapping("/material")
public class MaterialController {
	@Autowired
	MaterialService materialService;

	@Autowired
	CheckService checkService;

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/create")
	ResponseEntity<String> creatematerial(@RequestBody MaterialRequest materialRequest) {
		String message = null;
		Employee employee = employeeService.detailEmployee(materialRequest.getEmployeeId());
		Material materialTmp = null;
		if (employee.getBranch() == null) {
			materialTmp = materialService.detailMaterial(materialRequest.getCode(), employee.getRestaurant().getId(),
					"");
		} else {
			materialTmp = materialService.detailMaterial(materialRequest.getCode(), employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		if (materialTmp != null) {
			return ResponseEntity.badRequest().body("Nguyên liệu có mã code này đã tồn tại");
		} else if (checkService.checkCode(materialRequest.getCode())) {
			return ResponseEntity.badRequest().body("Mã code chứa kí tự đặc biệt");
		} else if (!checkService.checkName(materialRequest.getName())) {
			return ResponseEntity.badRequest().body("Tên nguyên liệu không hợp lệ");
		} else {
			Material material = new Material();
			material.setRestaurant(employee.getRestaurant());
			material.setBranch(employee.getBranch());
			material.setCode(materialRequest.getCode().toUpperCase());
			material.setName(materialRequest.getName());
			material.setCost(0);
			material.setType(materialRequest.getType());
			material.setQuantity(0);
			material.setWhereProduction(materialRequest.getWhereProduction());
			message = materialService.createMaterial(material) ? "Nguyên liệu đã được thêm vào" : "Không thành công";
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateMaterial(@RequestParam("employeeId") String employeeId,
			@RequestBody MaterialRequest materialRequest) {
		String message = null;
		Employee employee = employeeService.detailEmployee(employeeId);
		Material materialTmp = null;
		if (employee.getBranch() == null) {
			materialTmp = materialService.detailMaterial(materialRequest.getCode(), employee.getRestaurant().getId(),
					"");
		} else {
			materialTmp = materialService.detailMaterial(materialRequest.getCode(), employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		if (materialTmp == null) {
			return ResponseEntity.badRequest().body("Nguyên liệu này chưa có");
		}
		materialTmp.setRestaurant(employee.getRestaurant());
		materialTmp.setBranch(employee.getBranch());
		materialTmp.setCode(materialRequest.getCode());
		materialTmp.setName(materialRequest.getName());
		materialTmp.setType(materialRequest.getType());
		materialTmp.setWhereProduction(materialRequest.getWhereProduction());
		message = materialService.updateMaterial(materialTmp) ? "Cập nhật thông tin thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<?> detailMaterial(@RequestParam("code") String code, @RequestParam("employeeId") String employeeId) {
		MaterialRequest materialRequest = new MaterialRequest();
		Employee employee = employeeService.detailEmployee(employeeId);
		Material material = null;
		if (employee.getBranch() == null) {
			material = materialService.detailMaterial(code, employee.getRestaurant().getId(), "");
		} else {
			material = materialService.detailMaterial(code, employee.getRestaurant().getId(),
					employee.getBranch().getId());
		}
		if (material == null) {
			return ResponseEntity.badRequest().body("Nguyên liệu này chưa có");
		}
		materialRequest.setName(material.getName());
		materialRequest.setCode(material.getCode());
		materialRequest.setCost(material.getCost());
		materialRequest.setWhereProduction(material.getWhereProduction());
		return ResponseEntity.status(HttpStatus.OK).body(materialRequest);
	}

	@GetMapping("/list-material")
	ResponseEntity<?> listMaterial(@RequestParam("employeeId") String employeeId) {
		List<Material> listMaterial = null;
		Employee employee = employeeService.detailEmployee(employeeId);
		if (employee.getBranch() == null) {
			listMaterial = materialService.listMaterial(employee.getRestaurant().getId(), "");
		} else {
			listMaterial = materialService.listMaterial(employee.getRestaurant().getId(), employee.getBranch().getId());
		}
		List<MaterialRequest> listMaterialRequest = new ArrayList<>();
		for (Material material : listMaterial) {
			MaterialRequest materialRequest = new MaterialRequest();
			materialRequest.setCode(material.getCode());
			materialRequest.setName(material.getName());
			materialRequest.setCost(material.getCost());
			materialRequest.setType(material.getType());
			materialRequest.setWhereProduction(material.getWhereProduction());
			listMaterialRequest.add(materialRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listMaterialRequest);
	}
}
