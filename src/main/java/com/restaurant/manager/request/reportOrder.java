package com.restaurant.manager.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class reportOrder {
	
	@JsonProperty("foodTrending")
	List<foodOrderRequest> listfoodtrend;
	
	@JsonProperty("doanhthu")
	private float doanhthu;
	
	@JsonProperty("dathanhtoan")
	private int dathanhtoan;
	
	@JsonProperty("chuathanhtoan")
	private int chuathanhtoan;

	public List<foodOrderRequest> getListfoodtrend() {
		return listfoodtrend;
	}

	public void setListfoodtrend(List<foodOrderRequest> listfoodtrend) {
		this.listfoodtrend = listfoodtrend;
	}

	public float getDoanhthu() {
		return doanhthu;
	}

	public void setDoanhthu(float doanhthu) {
		this.doanhthu = doanhthu;
	}

	public int getDathanhtoan() {
		return dathanhtoan;
	}

	public void setDathanhtoan(int dathanhtoan) {
		this.dathanhtoan = dathanhtoan;
	}

	public int getChuathanhtoan() {
		return chuathanhtoan;
	}

	public void setChuathanhtoan(int chuathanhtoan) {
		this.chuathanhtoan = chuathanhtoan;
	}
}
