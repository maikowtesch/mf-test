package com.mftest.core.dto;

/**
 * Class that represents the request status exchanged with the clients. 
 */
public class Status {
	
	private int code;
	private String message;
	
	public Status(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
