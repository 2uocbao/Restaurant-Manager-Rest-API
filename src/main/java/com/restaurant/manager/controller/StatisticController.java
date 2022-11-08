package com.restaurant.manager.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.OrderRepository;
import com.restaurant.manager.repository.StatisticRepository;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	StatisticRepository statisticRepository;

	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/")
	ResponseEntity<?> getStatistic(@RequestParam("employeeId") String employeeId,
			@RequestParam("time start") Date timeStart, @RequestParam("time end") Date timeEnd) {
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
