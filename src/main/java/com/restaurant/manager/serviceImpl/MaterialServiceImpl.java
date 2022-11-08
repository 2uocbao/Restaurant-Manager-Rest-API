package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Material;
import com.restaurant.manager.repository.MaterialRepository;
import com.restaurant.manager.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	MaterialRepository materialRepository;

	@Override
	public boolean createMaterial(Material material) {
		return materialRepository.createMaterial(material);
	}

	@Override
	public boolean updateMaterial(Material material) {
		return materialRepository.updateMaterial(material);
	}

	@Override
	public Material detailMaterial(String code, String restaurantId, String branchId) {
		return materialRepository.detailMaterial(code, restaurantId, branchId);
	}

	@Override
	public List<Material> listMaterial(String restaurantId, String branchId) {
		return materialRepository.listMaterial(restaurantId, branchId);
	}
}
