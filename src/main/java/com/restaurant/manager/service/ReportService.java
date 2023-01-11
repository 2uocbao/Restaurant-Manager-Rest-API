package com.restaurant.manager.service;

import java.util.List;

import com.restaurant.manager.request.MaterialRequest;

public interface ReportService {

	// báo cáo doanh thu theo ngày, tháng, năm

	// báo cáo những nguyên liệu đã hết

	// báo cáo chi theo ngày tháng năm

	public Float incomebyDay(String day, String restaurantId, String branchId);

	public Float incomebyMonth(String day, String restaurantId, String branchId);

	public List<MaterialRequest> reportOutofStockMaterial(String restaurantId, String branchId);

	public Float spendbyDay(String employee);
}
