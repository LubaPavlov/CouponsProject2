/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyFacade.
 */
public class CompanyFacade implements CouponClientFacade {

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	private Company company = new Company();

	/**
	 * Instantiates a new company facade.
	 */
	public CompanyFacade() {
	}

	/**
	 * Instantiates a new company facade.
	 *
	 * @param companyDAO
	 * @param couponDAO
	 * @param company
	 *            the company object
	 */
	public CompanyFacade(CompanyDAO companyDAO, CouponDAO couponDAO, Company company) {
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
		this.company = company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company object
	 */
	// set the Facade for this company
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Login to the Coupon System with Company Client Type.
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client
	 * @param clientType
	 *            the client type
	 * @return the coupon client facade
	 * @throws LoginException
	 * @throws FacadeException
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException, FacadeException {

		try {
			if (!companyDAO.login(name, password)) {
				throw new FacadeException("Wrong user name or password");
			}
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
		CompanyFacade companyFacade = new CompanyFacade();
		try {
			this.company = companyDAO.getCompanyByName(name);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
		companyFacade.setCompany(this.company);
		return companyFacade;
	}

	/**
	 * A method to UPDATE Company details in the Company table except Company
	 * name and ID.
	 *
	 * @param company
	 *            the company object
	 * @throws FacadeException
	 * @throws DAOException
	 */
	public void updateCompany(Company company) throws FacadeException {
		Collection<Company> companies = getAllCompanies();
		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				try {
					companyDAO.updateCompany(company);
				} catch (DAOException e) {
					throw new FacadeException(e.getMessage());
				}
			}
		}
	}

	/**
	 * A method to CREATE a new coupon in the coupon table and ADD this Coupon
	 * to a logged in Company
	 *
	 * @param coupon
	 *            the coupon object
	 * @return true, if successful
	 * @throws FacadeException
	 * @throws DAOException
	 */
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
				System.out.println("A new coupon has been added");
				companyDAO.addCoupon(this.company, coupon.getCouponId());
			} catch (DAOException e) {
				throw new FacadeException(e.getMessage());
			}
		} else {
			System.out.println("Coupon with this title already exists");
		}
		return true;
	}

	/**
	 * A method to GET a collections of all companies (used in "update" method
	 * to check if company exists)
	 *
	 * @return the collection of all companies
	 * @throws FacadeException
	 */
	public Collection<Company> getAllCompanies() throws FacadeException {
		try {
			return companyDAO.getAllCompanies();
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	/**
	 * A method to GET a collections of all coupons of the logged in company
	 *
	 * @return the collection of all coupons
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllCoupons() throws FacadeException {
		try {
			return couponDAO.getAllCoupons();
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}
}
