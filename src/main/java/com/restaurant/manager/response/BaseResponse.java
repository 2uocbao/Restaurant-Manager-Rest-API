package com.restaurant.manager.response;

public class BaseResponse {
	private String message;
	private Object data;

	public BaseResponse() {

	}

	public BaseResponse(String message) {
		super();
		this.message = message;
	}

	public BaseResponse(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
