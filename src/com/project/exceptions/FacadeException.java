package com.project.exceptions;

public class FacadeException extends Exception {
	
	/**
	 * Constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new FacadeException.
	 */
	public FacadeException() {
		super();
	}

	/**
	 * Instantiates a new FacadeException with the given detail message.
	 * @param message
	 *            the message
	 */
	public FacadeException(String message) {
		super(message);
	}
	
	 /**
     * Instantiates a new FacadeException with the given root cause.
     * @param cause The root cause of the FacadeException.
     */
    public FacadeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a FacadeException with the given detail message and root cause.
     * @param message The detail message of the FacadeException.
     * @param cause The root cause of the FacadeException.
     */
    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }
	

}
