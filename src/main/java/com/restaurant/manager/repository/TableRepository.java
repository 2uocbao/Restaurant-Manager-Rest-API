package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Tables;

public interface TableRepository {

	public boolean createTable(Tables table);

	public Tables detailTable(int id);

	public boolean updateTable(Tables tables);

	public boolean changeStatusById(int id, int status);

	public List<Tables> listTableByBranchIdandRestaurantId(String restaurantId, String branchId);

	public Integer getStatusById(int id);

	public Tables getTablebyName(String restaurantId, String branchId, String name);

	public boolean changeStatusTableByRestaurantId(String restaurantId, int status);
	
	public boolean changeStatusTableByBranchId(String restaurantId, int status);
	
	public List<Tables> findTables(String restaurantId, String branchId, String keySearch);
}