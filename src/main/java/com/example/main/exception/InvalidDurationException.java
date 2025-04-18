package com.example.main.exception;

public class InvalidDurationException extends RuntimeException  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDurationException() {
		super("End time must be after sytart time");
	}

}
