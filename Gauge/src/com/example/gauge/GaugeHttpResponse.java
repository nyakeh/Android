package com.example.gauge;

public class GaugeHttpResponse {
	public int statusCode;
	public String content;
	
	public GaugeHttpResponse(int code, String text) {
		statusCode = code;
		content = text;
	}
}
