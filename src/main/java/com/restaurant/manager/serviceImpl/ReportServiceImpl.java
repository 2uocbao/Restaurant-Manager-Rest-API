package com.restaurant.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.ReportEntity;
import com.restaurant.manager.repository.ReportRepository;
import com.restaurant.manager.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepository reportRepository;

	@Override
	public boolean createReport(ReportEntity reportEntity) {
		return reportRepository.createReport(reportEntity);
	}

	@Override
	public ReportEntity detailReport(String restaurantId, String branchId) {
		return reportRepository.detailReport(restaurantId, branchId);
	}

	@Override
	public List<ReportEntity> listReport(String restaurantId, String branchId) {
		return reportRepository.listReport(restaurantId, branchId);
	}

}
