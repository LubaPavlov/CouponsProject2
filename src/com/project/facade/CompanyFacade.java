package com.project.facade;

import com.project.beans.Company;
import com.project.dao.CompanyDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
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
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		return null;
	}

}
