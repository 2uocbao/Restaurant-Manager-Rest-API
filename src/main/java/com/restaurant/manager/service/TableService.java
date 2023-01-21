package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.TableRequest;

public interface TableService {
	public String createTable(TableRequest tableRequest);

	public TableRequest detailTable(int tableId);

	public String updateTable(int tableId, TableRequest tableRequest);

	public List<TableRequest> listTableByBranchIdandRestaurantId(String restaurantId, String branchId);

	public List<TableRequest> listTableByStatus(String restaurantId, String branchId, int status);

	public List<TableRequest> findTable(String restaurantId, String branchId, String keySearch);
}
