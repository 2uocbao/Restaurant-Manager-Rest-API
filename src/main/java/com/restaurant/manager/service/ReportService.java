package com.restaurant.manager.service;

import com.restaurant.manager.request.ReportRequest;

public interface ReportService {

	public ReportRequest revenue(int employeeId, String fromDate, String toDate);
	
	public ReportRequest food(int employeeId, int foodId, String fromDate, String toDate);
	
	public ReportRequest warehouse(int employeeId, String fromDate, String toDate);
	
	public ReportRequest material(int employeeId, int materialId);
}
