package com.restaurant.manager.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Material;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.request.MaterialRequest;
import com.restaurant.manager.service.CheckService;
import com.restaurant.manager.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	MaterialRepository materialRepository;
	private CheckService checkService;
	private String success = "success";

	@Override
	public String createMaterial(MaterialRequest materialRequest) {
		String message = checkInfor(materialRequest);
		List<Material> materials = materialRepository.listMaterial(materialRequest.getRestaurantId(),
				materialRequest.getBranchId() == null ? "" : materialRequest.getBranchId());
		for (Material material : materials) {
			if (material.getName().equals(materialRequest.getName())) {
				return "Nguyên liệu có mã code này đã tồn tại";
			}
		}
		if (message.equals(success)) {
			Material material = new Material();
			material.setRestaurant(restaurantRepository.detailRestaurant(materialRequest.getRestaurantId()));
			material.setBranch(materialRequest.getBranchId() == null ? null
					: branchRepository.detailBranch(materialRequest.getBranchId()));
			material.setCode(materialRequest.getCode().toUpperCase());
			material.setName(materialRequest.getName());
			material.setCost(0);
			material.setType(materialRequest.getType());
			material.setStockEnd(materialRequest.getStockEnd());
			material.setQuantity(0);
			material.setWhereProduction(materialRequest.getWhereProduction());
			boolean successful = materialRepository.createMaterial(material);
			return successful ? success : "No success";
		}
		return message;
	}

	@Override
	public String updateMaterial(int materialId, MaterialRequest materialRequest) {
		String message = checkInfor(materialRequest);
		if (message.equals(success)) {
			Material material = materialRepository.detailMaterial(materialId);
			material.setCode(materialRequest.getCode());
			material.setName(materialRequest.getName());
			material.setType(materialRequest.getType());
			material.setStockEnd(materialRequest.getStockEnd());
			material.setWhereProduction(materialRequest.getWhereProduction());
			boolean successful = materialRepository.updateMaterial(material);
			return successful ? success : "No success";
		}
		return message;
	}

	@Override
	public MaterialRequest detailMaterial(int materialId) {
		Material material = materialRepository.detailMaterial(materialId);
		if (material != null) {
			MaterialRequest materialRequest = new MaterialRequest();
			materialRequest.setRestaurantId(material.getRestaurant().getId());
			materialRequest.setBranchId(material.getBranch() != null ? material.getBranch().getId() : null);
			materialRequest.setName(material.getName());
			materialRequest.setCode(material.getCode());
			materialRequest.setCost(material.getCost());
			materialRequest.setType(material.getType());
			materialRequest.setQuantity(material.getQuantity());
			materialRequest.setStockEnd(material.getStockEnd());
			materialRequest.setWhereProduction(material.getWhereProduction());
			return materialRequest;
		}
		return null;
	}

	@Override
	public List<MaterialRequest> listMaterial(String restaurantId, String branchId) {
		List<Material> materials = materialRepository.listMaterial(restaurantId, branchId == null ? "" : branchId);
		List<MaterialRequest> materialRequests = new ArrayList<>();
		for (Material material : materials) {
			MaterialRequest materialRequest = new MaterialRequest();
			materialRequest.setRestaurantId(material.getRestaurant().getId());
			materialRequest.setBranchId(material.getBranch() != null ? material.getBranch().getId() : null);
			materialRequest.setCode(material.getCode());
			materialRequest.setName(material.getName());
			materialRequest.setCost(material.getCost());
			materialRequest.setType(material.getType());
			materialRequest.setQuantity(material.getQuantity());
			materialRequest.setStockEnd(material.getStockEnd());
			materialRequest.setWhereProduction(material.getWhereProduction());
			materialRequests.add(materialRequest);
		}
		return materialRequests;
	}

	public String checkInfor(MaterialRequest materialRequest) {
		if (checkService.checkCode(materialRequest.getCode())) {
			return "Mã code chứa kí tự đặc biệt";
		} else if (!checkService.checkName(materialRequest.getName())) {
			return "Tên nguyên liệu không hợp lệ";
		}
		return success;
	}
}
