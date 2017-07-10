/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOException represents a generic exception. 
 * It should wrap any exception of the underlying code, such as SQLExceptions.
 */
public class DAOException extends RuntimeException{
	

	/**
	 * Constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAOException.
	 */
	public DAOException() {
		super();
	}

	/**
	 * Instantiates a new DAOException with the given detail message.
	 * @param message
	 *            the message
	 */
	public DAOException(String message) {
		super(message);
	}
	
	 /**
     * Instantiates a new DAOException with the given root cause.
     * @param cause The root cause of the DAOException.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DAOException with the given detail message and root cause.
     * @param message The detail message of the DAOException.
     * @param cause The root cause of the DAOException.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
	

}
