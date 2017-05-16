package com.project.facade;

import com.project.dao.CustomerDAO;
import com.project.main.ClientType;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO;

	public CustomerFacade() {
	}

	public CustomerFacade(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		return null;
	}

}
