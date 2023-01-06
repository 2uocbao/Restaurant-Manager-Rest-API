package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.BranchRequest;

public interface BranchService {
	public String createBranch(BranchRequest branchRequest);

	public BranchRequest detailBranch(String branchId);

	public String updateBranch(String branchId, BranchRequest branchRequest);

	public String changeStatusBranch(String branchId);
	
	public List<BranchRequest> listBranchByRestaurantId(String restaurantId);
}
