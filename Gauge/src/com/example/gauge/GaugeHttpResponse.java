package com.example.gauge;

public class GaugeHttpResponse {
	public int statusCode;
	public String httpMethod;
	public String content;
	
	public GaugeHttpResponse(int code, String requestMethod, String text) {
		statusCode = code;
		httpMethod= requestMethod;
		content = text;
	}
}
