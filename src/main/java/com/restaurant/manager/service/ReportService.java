package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.model.ReportEntity;

public interface ReportService {
	public boolean createReport(ReportEntity Report);

	public ReportEntity detailReport(String restaurantId, String branchId);

	public List<ReportEntity> listReport(String restaurantId, String branchId);

}
