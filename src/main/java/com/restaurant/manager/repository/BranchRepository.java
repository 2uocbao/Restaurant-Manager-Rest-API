package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Branch;

public interface BranchRepository {

	public boolean createBranch(Branch branch);

	public Branch detailBranch(String id);

	public boolean updateBranch(Branch branch);

	public boolean changeStatusBranch(String id, int status);

	public Branch getDetailByPhone(String phone);

	public Integer getStatusbyId(String id);

	public boolean changeStatusbyRestaurantId(String restaurantId, int status);
	
	public List<Branch> listBranchByRestaurantId(String restaurantId);
}