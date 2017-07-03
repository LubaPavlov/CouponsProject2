/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.beans;

import java.io.Serializable;
import java.util.Collection;
// TODO: Auto-generated Javadoc

/**
 * The Class Customer.
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private long custId;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	/**
	 * Default constructor Instantiates a new customer.
	 */
	public Customer() {
	}
 
	/**
	 * Instantiates a new customer.
	 *
	 * @param custName
	 *            the customer name
	 * @param password
	 *            the customer password
	 */
	public Customer(String custName, String password) {

		this.custName = custName;
		this.password = password;
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param custId
	 *            the customer id
	 * @param custName
	 *            the customer name
	 */
	public Customer(long custId, String custName) {

		this.custId = custId;
		this.custName = custName;

	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public long getCustId() {
		return custId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param custId
	 *            the new customer id
	 */
	public void setCustId(long custId) {
		checkCustId(custId);
		this.custId = custId;
	}

	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * Sets the customer name.
	 *
	 * @param custName
	 *            the new customer name
	 */
	public void setCustName(String custName) {
		checkCustName(custName);
		this.custName = custName;
	}

	/**
	 * Gets the customer password.
	 *
	 * @return the customer password
	 */
	public String getPassword() {
		checkPassword(password);
		return password;
	}

	/**
	 * Sets the customer password.
	 *
	 * @param password
	 *            the new customer password
	 */
	public void setPassword(String password) {
		checkPassword(password);
		this.password = password;
	}

	/**
	 * Gets the coupons.
	 *
	 * @return the coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * Sets the coupons.
	 *
	 * @param coupons
	 *            the new coupons
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

	/**
	 * Check customer id.
	 *
	 * @param custId
	 *            the customer id
	 */
	private void checkCustId(long custId) {
		if (custId == 0){
			throw new IllegalArgumentException("ID can't be zero");
		}
	}

	/**
	 * Check customer name.
	 *
	 * @param custName
	 *            the customer name
	 */
	private void checkCustName(String custName) {
		if (custName == null) {
			throw new NullPointerException("Name can't be null");
		}
	}

	/**
	 * Check customer password.
	 *
	 * @param password
	 *            the customer password
	 */
	private void checkPassword(String password) {
		if (password == null||password.isEmpty()) {
			throw new NullPointerException("Password can't be null or empty");
		}
	}

}
