package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Material;

public interface MaterialRepository {
	public boolean createMaterial(Material material);

	public boolean updateMaterial(Material material);

	public Material detailMaterial(int materialId);

	public List<Material> listMaterial(int restaurantId, int branchId);

	public List<Material> findMaterialByCode(int restaurantId, int branchId, String keySearch);
}
