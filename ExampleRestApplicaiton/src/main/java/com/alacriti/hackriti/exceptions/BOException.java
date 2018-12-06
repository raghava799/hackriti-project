package com.alacriti.hackriti.exceptions;

public class BOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -137227847816006429L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BOException(String message) {
		this.setMessage(message);
	}

}
