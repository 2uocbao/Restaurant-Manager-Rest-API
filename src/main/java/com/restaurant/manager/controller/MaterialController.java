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

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Material;
import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.request.MaterialRequest;
import com.restaurant.manager.service.BranchService;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.MaterialService;
import com.restaurant.manager.service.RestaurantService;

@RestController
@RequestMapping("/material")
public class MaterialController {
	@Autowired
	MaterialService materialService;

	@Autowired
	CheckService checkService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	BranchService branchService;

	@PostMapping("/create")
	ResponseEntity<String> creatematerial(@RequestBody MaterialRequest materialRequest) {
		String message;
		Material materialTmp = null;
		Branch branch = null;
		Restaurants restaurants = restaurantService.detailRestaurant(materialRequest.getRestaurantId());
		if (restaurants == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng không tồn tại");
		}
		if (materialRequest.getBranchId() != null) {
			branch = branchService.detailBranch(materialRequest.getBranchId());
			if (branch == null || !branch.getRestaurant().getId().equals(restaurants.getId())) {
				return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh không tồn tại");
			} else {
				branch = branchService.detailBranch(materialRequest.getBranchId());
			}
		}
		String branchId = materialRequest.getBranchId() != null ? materialRequest.getBranchId() : "";
		materialTmp = materialService.detailMaterial(materialRequest.getCode(), restaurants.getId(), branchId);
		if (materialTmp != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nguyên liệu có mã code này đã tồn tại");
		} else if (checkService.checkCode(materialRequest.getCode())) {
			return ResponseEntity.status(HttpStatus.OK).body("Mã code chứa kí tự đặc biệt");
		} else if (!checkService.checkName(materialRequest.getName())) {
			return ResponseEntity.status(HttpStatus.OK).body("Tên nguyên liệu không hợp lệ");
		}
		Material material = new Material();
		material.setRestaurant(restaurants);
		material.setBranch(branch);
		material.setCode(materialRequest.getCode().toUpperCase());
		material.setName(materialRequest.getName());
		material.setCost(0);
		material.setType(materialRequest.getType());
		material.setQuantity(0);
		material.setWhereProduction(materialRequest.getWhereProduction());
		message = materialService.createMaterial(material) ? "Nguyên liệu đã được thêm vào" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/update")
	ResponseEntity<String> updateMaterial(@RequestBody MaterialRequest materialRequest) {
		String message = null;
		String branchId = materialRequest.getBranchId() != null ? materialRequest.getBranchId() : "";
		Material materialTmp = materialService.detailMaterial(materialRequest.getCode(),
				materialRequest.getRestaurantId(), branchId);
		if (materialTmp == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nguyên liệu này chưa có");
		}
		materialTmp.setCode(materialRequest.getCode());
		materialTmp.setName(materialRequest.getName());
		materialTmp.setType(materialRequest.getType());
		materialTmp.setWhereProduction(materialRequest.getWhereProduction());
		message = materialService.updateMaterial(materialTmp) ? "Cập nhật thông tin thành công" : "Không thành công";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/detail")
	ResponseEntity<Object> detailMaterial(@RequestParam("code") String code, @RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		MaterialRequest materialRequest = new MaterialRequest();
		Branch branch = null;
		if (!branchId.equals("")) {
			branch = branchService.detailBranch(branchId);
			if (branch == null || !branch.getRestaurant().getId().equals(restaurantId)) {
				return ResponseEntity.status(HttpStatus.OK).body("Chi nhánh không tồn tại");
			}
		}
		Material material = materialService.detailMaterial(code, restaurantId, branchId);
		if (material == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Nguyên liệu mã " + code +" chưa có");
		}
		materialRequest.setRestaurantId(material.getRestaurant().getId());
		String branchid = material.getBranch() != null ? material.getBranch().getId() : null;
		materialRequest.setBranchId(branchid);
		materialRequest.setName(material.getName());
		materialRequest.setCode(material.getCode());
		materialRequest.setCost(material.getCost());
		materialRequest.setType(material.getType());
		materialRequest.setQuantity(material.getQuantity());
		materialRequest.setWhereProduction(material.getWhereProduction());
		return ResponseEntity.status(HttpStatus.OK).body(materialRequest);
	}

	@GetMapping("/list-material")
	ResponseEntity<Object> listMaterial(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		List<Material> listMaterial = materialService.listMaterial(restaurantId, branchId);
		List<MaterialRequest> listMaterialRequest = new ArrayList<>();
		for (Material material : listMaterial) {
			MaterialRequest materialRequest = new MaterialRequest();
			materialRequest.setRestaurantId(material.getRestaurant().getId());
			String branchid = material.getBranch() != null ? material.getBranch().getId() : null;
			materialRequest.setBranchId(branchid);
			materialRequest.setCode(material.getCode());
			materialRequest.setName(material.getName());
			materialRequest.setCost(material.getCost());
			materialRequest.setType(material.getType());
			materialRequest.setQuantity(material.getQuantity());
			materialRequest.setWhereProduction(material.getWhereProduction());
			listMaterialRequest.add(materialRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listMaterialRequest);
	}
}
