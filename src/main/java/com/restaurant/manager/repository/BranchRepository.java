package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Branch;

public interface BranchRepository {

	public Branch createBranch(Branch branch);

	public Branch detailBranch(int id);

	public Branch updateBranch(Branch branch);

	public Branch changeStatusBranch(int id, int status);

	public Branch getDetailByPhone(String phone);

	public Integer getStatusbyId(int id);

	public Branch changeStatusbyRestaurantId(int restaurantId, int status);
	
	public List<Branch> listBranchByRestaurantId(int restaurantId);
}