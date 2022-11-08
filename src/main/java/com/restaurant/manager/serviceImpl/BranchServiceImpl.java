package com.restaurant.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.TableRepository;
import com.restaurant.manager.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TableRepository tableRepository;

	@Override
	public boolean createBranch(Branch branch) {
		return branchRepository.createBranch(branch);
	}

	@Override
	public Branch detailBranch(String id) {

		return branchRepository.detailBranch(id);
	}

	@Override
	public boolean updateBranch(Branch branch) {
		return branchRepository.updateBranch(branch);
	}

	@Override
	public boolean changeStatusBranch(String id, int status) {
		if (status == 0) {
			employeeRepository.changeStatusEmployeeByBranchId(id, status);
			tableRepository.changeStatusTableByBranchId(id, status);
		}
		return branchRepository.changeStatusBranch(id, status);
	}

	@Override
	public Branch getDetailByPhone(String phone) {
		return branchRepository.getDetailByPhone(phone);
	}

	@Override
	public Integer getStatusbyId(String id) {
		return branchRepository.getStatusbyId(id);
	}

}
