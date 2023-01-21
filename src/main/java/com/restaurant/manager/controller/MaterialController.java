package com.restaurant.manager.controller;

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

import com.restaurant.manager.request.MaterialRequest;
import com.restaurant.manager.response.BaseResponse;
import com.restaurant.manager.service.MaterialService;

@RestController
@RequestMapping("/material")
public class MaterialController {
	@Autowired
	MaterialService materialService;
	private String success = "success";

	@PostMapping("/create")
	ResponseEntity<BaseResponse> creatematerial(@RequestBody MaterialRequest materialRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = materialService.createMaterial(materialRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(materialRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@PutMapping("/update")
	ResponseEntity<BaseResponse> updateMaterial(@Valid @RequestParam("id") int id,
			@RequestBody MaterialRequest materialRequest) {
		BaseResponse baseResponse = new BaseResponse();
		String message = materialService.updateMaterial(id, materialRequest);
		if (message.equals(success)) {
			baseResponse.setStatus(200);
			baseResponse.setMessage(message);
			baseResponse.setData(materialRequest);
		} else {
			baseResponse.setStatus(404);
			baseResponse.setMessage(message);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/detail")
	ResponseEntity<BaseResponse> detailMaterial(@RequestParam("id") int id) {
		BaseResponse baseResponse = new BaseResponse();
		MaterialRequest materialRequest = materialService.detailMaterial(id);
		if (materialRequest == null) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(materialRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/list-material")
	ResponseEntity<BaseResponse> listMaterial(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId) {
		BaseResponse baseResponse = new BaseResponse();
		List<MaterialRequest> materialRequests = materialService.listMaterial(restaurantId, branchId);
		if (materialRequests.isEmpty()) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(materialRequests);
		}

		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}

	@GetMapping("/find-material")
	ResponseEntity<BaseResponse> findMaterialbyCode(@RequestParam("branchId") String branchId,
			@RequestParam("restaurantId") String restaurantId, @RequestParam("keySearch") String keySearch) {
		BaseResponse baseResponse = new BaseResponse();
		List<MaterialRequest> materialRequests = materialService.findMaterialByCode(restaurantId, branchId, keySearch);
		if (materialRequests.isEmpty()) {
			baseResponse.setStatus(404);
			baseResponse.setMessage("Not Found");
		} else {
			baseResponse.setStatus(200);
			baseResponse.setMessage(success);
			baseResponse.setData(materialRequests);
		}
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
}
