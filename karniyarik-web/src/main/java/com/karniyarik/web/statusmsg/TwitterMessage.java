package com.karniyarik.web.statusmsg;


public class TwitterMessage{
	private String message;
	
	public TwitterMessage() {
		
	}

	public TwitterMessage(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
