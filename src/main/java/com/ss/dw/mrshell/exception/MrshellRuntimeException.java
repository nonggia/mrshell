package com.ss.dw.mrshell.exception;

public class MrshellRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5939348445848947815L;

	public MrshellRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public MrshellRuntimeException(String message) {
		super(message);
	}
}
