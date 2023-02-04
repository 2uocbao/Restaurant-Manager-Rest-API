package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.BranchRequest;

public interface BranchService {
	public String createBranch(BranchRequest branchRequest);

	public BranchRequest detailBranch(int branchId);

	public String updateBranch(int branchId, BranchRequest branchRequest);

	public String changeStatusBranch(int branchId);
	
	public List<BranchRequest> listBranchByRestaurantId(int restaurantId);
}
