package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.TableRequest;

public interface TableService {
	public String createTable(TableRequest tableRequest);

	public TableRequest detailTable(int tableId);

	public String updateTable(int tableId, TableRequest tableRequest);

	public List<TableRequest> listTableByBranchIdandRestaurantId(int restaurantId, int branchId);

	public List<TableRequest> listTableByStatus(int restaurantId, int branchId, int status);

	public List<TableRequest> findTable(int restaurantId, int branchId, String keySearch);
}
