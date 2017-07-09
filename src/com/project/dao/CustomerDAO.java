/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.CouponSystemException;

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
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void createCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Removes the customer.
	 *
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Update customer.
	 *
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Gets the customer.
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Customer getCustomer(long custId) throws CouponSystemException;

	/**
	 * Gets the all customers.
	 *
	 * @return the collection of all customers
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * Gets the coupons.
	 *
	 * @param customer
	 *            the customer object
	 * @return the coupons collection
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;

	/**
	 * Login.
	 *
	 * @param custName
	 *            the customer's name
	 * @param password
	 *            the customer's password
	 * @return true, if successful
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public boolean login(String custName, String password) throws CouponSystemException;

	/**
	 * Gets the customer id.
	 *
	 * @param custName
	 *            the customer's name
	 * @return the customer's id
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public long getCustomerId(String custName) throws CouponSystemException;

	/**
	 * Adds the coupon to customer.
	 *
	 * @param coupon
	 *            the coupon object
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void addCouponToCustomer(Coupon coupon, Customer customer) throws CouponSystemException;

	/**
	 * Gets the customer by name.
	 *
	 * @param name
	 *            the name of the customer
	 * @return the customer object by name
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Customer getCustomerByName(String name) throws CouponSystemException;

}
