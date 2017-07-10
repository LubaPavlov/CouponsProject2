package com.project.facade;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CouponDBDAO;
import com.project.dao.CustomerDAO;
import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
import com.project.main.ClientType;

public class CompanyFacade implements CouponClientFacade {
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	private Company company = new Company();
	private Coupon coupon;

	public CompanyFacade() {
	}

	public CompanyFacade(CompanyDAO companyDAO, CouponDAO couponDAO, Company company) {
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
		this.company = company;
	}

	// set the Facade for this company
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException, FacadeException {

		if (clientType != ClientType.COMPANY) {
			System.out.println("Error: clinet type is not a company");
			throw new IndexOutOfBoundsException("Error: clinet type is not a company");
		}

		try {
			if (!companyDAO.login(name, password)) {
				System.out.println("Login failed");
				throw new IndexOutOfBoundsException("Wrong user name or password");
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}

		CompanyFacade companyFacade = new CompanyFacade();
		this.company = companyDAO.getCompanyByName(name);
		companyFacade.setCompany(this.company);
		return companyFacade;

	}

	public void updateCompany(Company company) throws FacadeException {

		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyDAO.updateCompany(company);
			} else {
				throw new DAOException();
			}
		}
	}

	public boolean createCoupon(Coupon coupon) throws FacadeException {
		boolean couponExist = false;
		// Create new List of all existing
		Collection<Coupon> allCoupons = getAllCoupons();
		for (Coupon existingCoupon : allCoupons) {
			if (existingCoupon.getTitle().equals(coupon.getTitle())) {
				couponExist = true;
				break;
			}
		}
		if (!couponExist) {
			
			try {
				couponDAO.createCoupon(coupon);
			} catch (Exception e) {
				
			}	
			
			companyDAO.addCoupon(this.company, coupon.getCouponId());			
		}
		
		else {
			
			throw new DAOException("Coupon with this title already exists");
		}
		
		return true;

	}

	public Collection<Company> getAllCompanies() throws FacadeException {
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	public Collection<Coupon> getAllCoupons() throws FacadeException {
		if (couponDAO == null) {
			return null;
		}
		return couponDAO.getAllCoupons();
	}
}
