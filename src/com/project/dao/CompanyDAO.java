/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.util.Collection;

import com.project.beans.*;
import com.project.exceptions.DAOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface CompanyDAO.
 */
public interface CompanyDAO {
	
	/**
	 * A method to CREATE the given company in the database with auto-generated ID
	 *
	 * @param Company
	 *            the company to be created
	 * @throws DAOException If something fails at database level.
	 */
	public void createCompany(Company Company) throws DAOException;

	/**
	 * A method to REMOVE Company from the Company table
	 *
	 * @param Company
	 *            the company to be deleted
	 * @throws DAOException If something fails at database level.
	 */
	public void removeCompany(Company Company) throws DAOException;

	/**
	 * Update the company.
	 *
	 * @param Company
	 *            the company to be updated
	 * @throws DAOException If something fails at database level.
	 */
	public void updateCompany(Company Company)throws DAOException;

	/**
	 * Gets the company by company ID.
	 *
	 * @param comp_id
	 *            the company id
	 * @return the company
	 * @throws DAOException If something fails at database level.
	 */
	public Company getCompany(long comp_id) throws DAOException;

	/**
	 * Gets the list of all companies.
	 *
	 * @return the all companies
	 * @throws DAOException If something fails at database level.
	 */
	public Collection<Company> getAllCompanies() throws DAOException;

	/**
	 * Gets the list of coupons by company ID.
	 *
	 * @param compId
	 *            the company id
	 * @return the coupons of the provided company ID 
	 * @throws DAOException If something fails at database level.
	 */
	public Collection<Coupon> getCoupons(long compId) throws DAOException;
	
	/**
	 * Gets the company id by company name.
	 *
	 * @param compName
	 *            the company name
	 * @return the company id
	 * @throws DAOException If something fails at database level.
	 */
	public long getCompanyId(String compName) throws DAOException;
	
	/**
	 * Gets the company by name.
	 *
	 * @param name
	 *            the name of the company
	 * @return the company object by name
	 * @throws DAOException If something fails at database level.
	 */
	public Company getCompanyByName(String name) throws DAOException;
	
	/**
	 * Add a new COUPON to the company_coupon JOIN table
	 *
	 * @param Company
	 *            the company that coupon will be added
	 * @throws DAOException If something fails at database level.
	 */
	
	public void addCoupon(Company company, long couponId)throws DAOException;
	
	/**
	 * Login.
	 *
	 * @param compName
	 *            the company name
	 * @param password
	 *            the company password
	 * @return true, if successful
	 * @throws DAOException If something fails at database level.
	 */
	public boolean login(String compName, String password) throws DAOException;

}
