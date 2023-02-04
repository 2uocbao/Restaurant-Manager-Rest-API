package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Tables;

public interface TableRepository {

	public boolean createTable(Tables table);

	public Tables detailTable(int id);

	public boolean updateTable(Tables tables);

	public boolean changeStatusById(int id, int status);

	public List<Tables> listTableByBranchIdandRestaurantId(int restaurantId, int branchId);

	public Integer getStatusById(int id);

	public boolean changeStatusTableByRestaurantId(int restaurantId, int status);
	
	public boolean changeStatusTableByBranchId(int restaurantId, int status);
	
	public List<Tables> findTables(int restaurantId, int branchId, String keySearch);
}