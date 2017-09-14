package com.spring.common.web.filter;

public class PerformanceCounter {
	private long start;
	private long end;
	
	public void start() {
		this.start = System.currentTimeMillis();
	}
	
	public void end() {
		this.end = System.currentTimeMillis();
	}
	
	public long spend() {
		return end - start;
	}
	
	public String toString() {
		return spend() + " : " + start + " - " + end + " ";
	}
}
