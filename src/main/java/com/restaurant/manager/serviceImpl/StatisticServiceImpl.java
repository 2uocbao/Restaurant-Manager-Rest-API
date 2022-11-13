package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.ReportEntity;
import com.restaurant.manager.repository.StatisticRepository;
import com.restaurant.manager.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	StatisticRepository statisticRepository;

	@Override
	public boolean createStatistic(ReportEntity statistic) {
		return statisticRepository.createStatistic(statistic);
	}

	@Override
	public ReportEntity detailStatistic(String restaurantId, String branchId) {
		return statisticRepository.detailStatistic(restaurantId, branchId);
	}

	@Override
	public List<ReportEntity> listStatistic(String restaurantId, String branchId) {
		return statisticRepository.listStatistic(restaurantId, branchId);
	}

}
