package com.project.facade;

import javax.security.auth.login.LoginException;

import com.project.beans.Company;
import com.project.dao.CompanyDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
import com.project.exceptions.DAOException;
import com.project.main.ClientType;

public class CompanyFacade implements CouponClientFacade {
	private CompanyDAO companyDAO ;
	private CouponDAO couponDAO;

	public CompanyFacade() {
	}

	public CompanyFacade(CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginException {

		try {
			if (clientType == ClientType.COMPANY && companyDAO.login(name, password)== true) {
				CompanyFacade companyFacade  = new CompanyFacade(companyDAO,couponDAO);
				return companyFacade;
			}
			else return null;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
