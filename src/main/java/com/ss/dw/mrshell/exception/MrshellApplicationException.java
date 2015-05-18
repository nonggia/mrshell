package com.ss.dw.mrshell.exception;

public class MrshellApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455537302847690933L;

	public MrshellApplicationException(String message, Exception e) {
		super(message, e);
	}

	public MrshellApplicationException(String message) {
		super(message);
	}
}
