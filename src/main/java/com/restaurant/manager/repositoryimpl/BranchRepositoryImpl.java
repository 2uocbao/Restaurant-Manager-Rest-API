package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.repository.BranchRepository;

@Repository
@Transactional
public class BranchRepositoryImpl extends AbtractRepositoryImpl<Branch> implements BranchRepository {

	@Override
	public Branch createBranch(Branch branch) {
		return this.create(branch);
	}

	@Override
	public Branch detailBranch(int id) {
		return this.getDetail(id);
	}

	@Override
	public Branch updateBranch(Branch branch) {
		return this.update(branch);
	}

	@Override
	public Branch changeStatusBranch(int id, int status) {
		Branch branch = this.getDetail(id);
		branch.setStatus(status);
		this.update(branch);
		return branch;
	}

	@Override
	public Branch getDetailByPhone(String phone) {
		return this.callQueryForEntity("FROM com.restaurant.manager.model.Branch b WHERE b.phone = :" + phone);

	}

	@Override
	public Integer getStatusbyId(int id) {
		Branch branch = this.getDetail(id);
		return branch.getStatus();
	}

	@Override
	public Branch changeStatusbyRestaurantId(int restaurantId, int status) {
		
		return this.callQueryForEntity("UPDATE com.restaurant.manager.model.Branch b SET b.status = :" + status + "WHERE b.restaurant.id = :" + restaurantId);
	}

	@Override
	public List<Branch> listBranchByRestaurantId(int restaurantId) {
		List<Branch> branchs = new ArrayList<>();
		this.getList().stream().forEach(

				i -> {
					if (i.getRestaurant().getId() == restaurantId) {
						branchs.add(i);
					}
				});
		return branchs;
	}
}
