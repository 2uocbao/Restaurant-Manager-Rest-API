package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.MaterialRequest;

public interface MaterialService {
	public String createMaterial(MaterialRequest materialRequest);

	public String updateMaterial(int materialId, MaterialRequest materialRequest);

	public MaterialRequest detailMaterial(int materialId);

	public List<MaterialRequest> listMaterial(int restaurantId, int branchId);

	public List<MaterialRequest> findMaterialByCode(int restaurantId, int branchId, String keySearch);
}
