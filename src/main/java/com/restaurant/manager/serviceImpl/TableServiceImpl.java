package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Tables;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.service.TableService;

@Service
public class TableServiceImpl implements TableService {

	@Autowired
	TableRepository tableRepository;

	@Override
	public boolean createTable(Tables table) {
		return tableRepository.createTable(table);
	}

	@Override
	public Tables detailTable(int id) {
		return tableRepository.detailTable(id);
	}

	@Override
	public boolean updateTable(Tables tables) {
		return tableRepository.updateTable(tables);
	}

	@Override
	public boolean changeStatusById(int id, int status) {
		return tableRepository.changeStatusById(id, status);
	}

	@Override
	public List<Tables> listTableByBranchIdandRestaurantId(String restaurantId, String branchId) {
		return tableRepository.listTableByBranchIdandRestaurantId(restaurantId, branchId);
	}

	@Override
	public Integer getStatusById(int id) {
		return tableRepository.getStatusById(id);
	}

	@Override
	public Tables getTablebyName(String restaurantId, String branchId, String name) {
		return tableRepository.getTablebyName(restaurantId, branchId, name);
	}

	@Override
	public List<Tables> listTableByStatus(String restaurantId, String branchId, int status) {
		return tableRepository.listTableByStatus(restaurantId, branchId, status);
	}

}
