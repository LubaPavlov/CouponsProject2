package com.project.facade;

import javax.security.auth.login.LoginException;

import com.project.beans.Company;
import com.project.dao.CompanyDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
import com.project.exceptions.CouponSystemException;
import com.project.main.ClientType;

public class CompanyFacade implements CouponClientFacade {
	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;

	public CompanyFacade() {
	}

	public CompanyFacade(CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginException{

		if (clientType != ClientType.COMPANY) {
			System.out.println("Error: clinet type is not a company");
			throw new IndexOutOfBoundsException("Error: clinet type is not a company");
		}

		try {
			if (!companyDAO.login(name, password)) {
				System.out.println("Login failed");
				throw new IndexOutOfBoundsException("Wrong user name or password");	
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		CompanyFacade facade = new CompanyFacade();
		return facade;

	}

}
