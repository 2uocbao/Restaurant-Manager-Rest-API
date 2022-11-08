package com.restaurant.manager.service;

import com.restaurant.manager.model.Branch;

public interface BranchService {
	public boolean createBranch(Branch branch);

	public Branch detailBranch(String id);

	public boolean updateBranch(Branch branch);

	public boolean changeStatusBranch(String id, int status);
	
	public Branch getDetailByPhone(String phone);
	
	public Integer getStatusbyId(String id);
}
