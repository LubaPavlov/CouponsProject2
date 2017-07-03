package com.project.facade;

import javax.security.auth.login.LoginException;

import com.project.exceptions.CouponSystemException;
import com.project.main.ClientType;

public interface CouponClientFacade {
	
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginException;

}
