package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.Tables;

public interface TableService {
	public boolean createTable(Tables table);

	public Tables detailTable(int id);

	public boolean updateTable(Tables tables);

	public boolean changeStatusById(int id, int status);

	public List<Tables> listTableByBranchIdandRestaurantId(String restaurantId, String branchId);

	public Integer getStatusById(int id);

	public Tables getTablebyName(String restaurantId, String branchId, String name);

	public List<Tables> listTableByStatus(String restaurantId, String branchId, int status);

}
