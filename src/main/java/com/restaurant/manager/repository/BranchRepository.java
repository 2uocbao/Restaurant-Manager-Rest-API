package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Branch;

public interface BranchRepository {

	public boolean createBranch(Branch branch);

	public Branch detailBranch(int id);

	public boolean updateBranch(Branch branch);

	public boolean changeStatusBranch(int id, int status);

	public Branch getDetailByPhone(String phone);

	public Integer getStatusbyId(int id);

	public boolean changeStatusbyRestaurantId(int restaurantId, int status);
	
	public List<Branch> listBranchByRestaurantId(int restaurantId);
}