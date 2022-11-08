package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Material;

public interface MaterialRepository {
	public boolean createMaterial(Material material);

	public boolean updateMaterial(Material material);

	public Material detailMaterial(String code, String restaurantId, String branchId);
	
	public List<Material> listMaterial(String restaurantId, String branchId);
}
