package imports.d20170427coupProjDafnaWeiss.exceptions;
/**
 * Dealing with all the coupon system exceptions
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class MyException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Send an error message of the occurred exception
	 * @param message The exception error message
	 */
	public MyException(String message){
		super(message);
	}
}
