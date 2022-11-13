package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.ReportEntity;

public interface StatisticRepository {

	public boolean createStatistic(ReportEntity statistic);

	public ReportEntity detailStatistic(String restaurantId, String branchId);

	public List<ReportEntity> listStatistic(String restaurantId, String branchId);
}
