package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Material;

public interface MaterialService {
	public boolean createMaterial(Material ingredient);

	public boolean updateMaterial(Material ingredient);

	public Material detailMaterial(String code, String restaurantId, String branchId);

	public List<Material> listMaterial(String restaurantId, String branchId);
}
