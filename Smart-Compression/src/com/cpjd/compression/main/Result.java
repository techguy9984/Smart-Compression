package com.cpjd.compression.main;

public class Result {

	/**
	 * In nanoseconds
	 */
	private long time;
	/**
	 * In bytes
	 */
	private long size;
	
	public Result(long size, long time) {
		this.time = time;
		this.size = size;
	}
	
	public long getTime() {
		return time;
	}
	
	public long getSize() {
		return size;
	}
	
}
