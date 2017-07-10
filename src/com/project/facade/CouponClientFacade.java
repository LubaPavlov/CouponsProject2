/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.facade;

import javax.security.auth.login.LoginException;

import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
import com.project.main.ClientType;

// TODO: Auto-generated Javadoc
/**
 * The Interface CouponClientFacade.
 */
public interface CouponClientFacade {
	
	/**
	 * Login.
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client
	 * @param clientType
	 *            the client type
	 * @return the coupon client facade
	 * @throws LoginException
	 *             the login exception
	 * @throws DAOException
	 *             the coupon system exception
	 * @throws FacadeException 
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginException, FacadeException;

}
