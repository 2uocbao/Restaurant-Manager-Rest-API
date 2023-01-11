package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Restaurant;
import com.restaurant.manager.model.Tables;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.request.TableRequest;
import com.restaurant.manager.service.TableService;

@Service
public class TableServiceImpl implements TableService {

	@Autowired
	TableRepository tableRepository;
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	BranchRepository branchRepository;
	private String success = "success";

	@Override
	public String createTable(TableRequest tableRequest) {
		List<Tables> tables = tableRepository.listTableByBranchIdandRestaurantId(tableRequest.getRestaurantId(),
				tableRequest.getBranchId() == null ? "" : tableRequest.getBranchId());
		for (Tables table : tables) {
			if (table.getName().equals(tableRequest.getName())) {
				return "Bàn đã tồn tại";
			}
		}
		Restaurant restaurant = restaurantRepository.detailRestaurant(tableRequest.getRestaurantId());
		Branch branch = tableRequest.getBranchId() == null ? null
				: branchRepository.detailBranch(tableRequest.getBranchId());
		Tables table = new Tables();
		table.setRestaurant(restaurant);
		table.setBranch(branch);
		table.setName(tableRequest.getName().replaceAll("//s+", " ").trim().toUpperCase());
		table.setTotalSlot(tableRequest.getTotalSlot());
		table.setDescription(tableRequest.getDescription().replaceAll("//s+", " ").trim());
		table.setStatus(0);
		boolean successful = tableRepository.createTable(table);
		return successful ? success : "No success";
	}

	@Override
	public TableRequest detailTable(int tableId) {
		Tables table = tableRepository.detailTable(tableId);
		TableRequest tableRequest = new TableRequest();
		tableRequest.setTableId(table.getId());
		tableRequest.setRestaurantId(table.getRestaurant().getId());
		tableRequest.setBranchId(table.getBranch() != null ? table.getBranch().getId() : null);
		tableRequest.setName(table.getName());
		tableRequest.setTotalSlot(table.getTotalSlot());
		tableRequest.setDescription(table.getDescription());
		tableRequest.setStatus(table.getStatus());
		return tableRequest;
	}

	@Override
	public String updateTable(int tableId, TableRequest tableRequest) {
		Tables tableUp = tableRepository.detailTable(tableId);
		List<Tables> tables = tableRepository.listTableByBranchIdandRestaurantId(tableRequest.getRestaurantId(),
				tableRequest.getBranchId() == null ? "" : tableRequest.getBranchId());
		for (Tables table : tables) {
			if (table.getName().equals(tableRequest.getName()) && !table.getName().equals(tableRequest.getName())) {
				return "Bàn đã tồn tại";
			}
		}
		tableUp.setName(tableRequest.getName().toUpperCase());
		tableUp.setTotalSlot(tableRequest.getTotalSlot());
		tableUp.setDescription(tableRequest.getDescription().replaceAll("\\s+", " ").trim());
		boolean successful = tableRepository.updateTable(tableUp);
		return successful ? success : "No success";
	}

	@Override
	public List<TableRequest> listTableByBranchIdandRestaurantId(String restaurantId, String branchId) {
		List<TableRequest> tableRequests = new ArrayList<>();
		List<Tables> tables = tableRepository.listTableByBranchIdandRestaurantId(restaurantId,
				branchId == null ? "" : branchId);
		for (Tables table : tables) {
			TableRequest tableRequest = new TableRequest();
			tableRequest.setTableId(table.getId());
			tableRequest.setRestaurantId(table.getRestaurant().getId());
			tableRequest.setBranchId(branchId);
			tableRequest.setName(table.getName());
			tableRequest.setTotalSlot(table.getTotalSlot());
			tableRequest.setDescription(table.getDescription());
			tableRequest.setStatus(table.getStatus());
			tableRequests.add(tableRequest);
		}
		return tableRequests;
	}

	@Override
	public List<TableRequest> listTableByStatus(String restaurantId, String branchId, int status) {
		List<TableRequest> tableRequests = new ArrayList<>();
		List<Tables> tables = tableRepository.listTableByBranchIdandRestaurantId(restaurantId,
				branchId == null ? "" : branchId);
		for (Tables table : tables) {
			if (table.getStatus() == status) {
				TableRequest tableRequest = new TableRequest();
				tableRequest.setTableId(table.getId());
				tableRequest.setRestaurantId(table.getRestaurant().getId());
				tableRequest.setBranchId(branchId);
				tableRequest.setName(table.getName());
				tableRequest.setTotalSlot(table.getTotalSlot());
				tableRequest.setDescription(table.getDescription());
				tableRequest.setStatus(table.getStatus());
				tableRequests.add(tableRequest);
			}
		}
		return tableRequests;
	}
}
