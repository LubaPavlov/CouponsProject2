package imports.d20170427coupProjDafnaWeiss.facade;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
/**
 * Facade Interface: Login to the coupon system according to the Client Type (ADMIN/COMPANY/CUSTOMER)
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public interface CouponClientFacade {
	/**
	 * Login to the Coupon System according to the Client Type
	 * @param name The Client type name that logged in the system
	 * @param Password The Client type password that logged in the system
	 * @return	True upon login success otherwise False
	 * @throws MyException SQL exception
	 */
	public boolean login (String name, String Password) throws MyException;
}
