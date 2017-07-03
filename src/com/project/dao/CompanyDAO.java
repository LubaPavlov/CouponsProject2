/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.util.Collection;

import com.project.beans.*;
import com.project.exceptions.CouponSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface CompanyDAO.
 */
public interface CompanyDAO {
	
	/**
	 * Creates a new company.
	 *
	 * @param Company
	 *            the company to be created
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void createCompany(Company Company) throws CouponSystemException;

	/**
	 * Deletes the company.
	 *
	 * @param Company
	 *            the company to be deleted
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void removeCompany(Company Company) throws CouponSystemException;

	/**
	 * Update the company.
	 *
	 * @param Company
	 *            the company to be updated
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void updateCompany(Company Company)throws CouponSystemException;

	/**
	 * Gets the company by company ID.
	 *
	 * @param comp_id
	 *            the company id
	 * @return the company
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Company getCompany(long comp_id) throws CouponSystemException;

	/**
	 * Gets the list of all companies.
	 *
	 * @return the all companies
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * Gets the list of coupons by company ID.
	 *
	 * @param compId
	 *            the company id
	 * @return the coupons of the provided company ID 
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Collection<Coupon> getCoupons(long compId) throws CouponSystemException;
	
	/**
	 * Gets the company id by company name.
	 *
	 * @param compName
	 *            the company name
	 * @return the company id
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public long getCompanyId(String compName) throws CouponSystemException;

	/**
	 * Login.
	 *
	 * @param compName
	 *            the company name
	 * @param password
	 *            the company password
	 * @return true, if successful
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public boolean login(String compName, String password) throws CouponSystemException;

}
