package com.restaurant.manager.service;

public interface ReportService {

	// báo cáo doanh thu theo ngày, tháng, năm

	// báo cáo những nguyên liệu đã hết

	// báo cáo chi theo ngày tháng năm

	public Float incomebyDay(String day, String restaurantId, String branchId);

	public Float incomebyMonth(String day, String employeeId);

	public Float incomebyYear(int year, String employeeId);

	public String reportOutofStock(String employeeId);

	public Float spendbyDay(String employee);
}
