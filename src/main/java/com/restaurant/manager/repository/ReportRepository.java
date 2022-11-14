package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.ReportEntity;

public interface ReportRepository {

	public boolean createReport(ReportEntity Report);

	public ReportEntity detailReport(String restaurantId, String branchId);

	public List<ReportEntity> listReport(String restaurantId, String branchId);
}
