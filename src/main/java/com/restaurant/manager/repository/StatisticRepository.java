package com.restaurant.manager.repository;

import java.util.List;

import com.restaurant.manager.model.Statistics;

public interface StatisticRepository {

	public boolean createStatistic(Statistics statistic);

	public Statistics detailStatistic(String restaurantId, String branchId);

	public List<Statistics> listStatistic(String restaurantId, String branchId);
}
