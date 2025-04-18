package com.example.main.exception;

public class InvalidPassword extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidPassword(String msg) {
		super(msg);
	}
}
