package com.project.facade;

import com.project.main.ClientType;

public interface CouponClientFacade {
	
	public CouponClientFacade login(String name, String password, ClientType clientType);

}
