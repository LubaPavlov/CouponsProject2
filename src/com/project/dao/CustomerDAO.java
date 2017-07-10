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
 * The Interface CustomerDAO.
 */
public interface CustomerDAO {

	/**
	 * Creates the customer.
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException If something fails at database level.
	 */
	public void createCustomer(Customer customer) throws DAOException;

	/**
	 * Removes the customer.
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException If something fails at database level.
	 */
	public void removeCustomer(Customer customer) throws DAOException;

	/**
	 * Update customer.
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException If something fails at database level.
	 */
	public void updateCustomer(Customer customer) throws DAOException;

	/**
	 * Gets the customer.
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer object
	 * @throws DAOException If something fails at database level.
	 */
	public Customer getCustomer(long custId) throws DAOException;

	/**
	 * Gets the all customers.
	 *
	 * @return the collection of all customers
	 * @throws DAOException If something fails at database level.
	 */
	public Collection<Customer> getAllCustomers() throws DAOException;

	/**
	 * Gets the coupons.
	 *
	 * @param customer
	 *            the customer object
	 * @return the coupons collection
	 * @throws DAOException If something fails at database level.
	 */
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException;

	/**
	 * Login.
	 *
	 * @param custName
	 *            the customer's name
	 * @param password
	 *            the customer's password
	 * @return true, if successful
	 * @throws DAOException If something fails at database level.
	 */
	public boolean login(String custName, String password) throws DAOException;

	/**
	 * Gets the customer id.
	 *
	 * @param custName
	 *            the customer's name
	 * @return the customer's id
	 * @throws DAOException If something fails at database level.
	 */
	public long getCustomerId(String custName) throws DAOException;

	/**
	 * Adds the coupon to customer.
	 *
	 * @param coupon
	 *            the coupon object
	 * @param customer
	 *            the customer object
	 * @throws DAOException If something fails at database level.
	 */
	public void addCouponToCustomer(Coupon coupon, Customer customer) throws DAOException;

	/**
	 * Gets the customer by name.
	 *
	 * @param name
	 *            the name of the customer
	 * @return the customer object by name
	 * @throws DAOException If something fails at database level.
	 */
	public Customer getCustomerByName(String name) throws DAOException;

}
