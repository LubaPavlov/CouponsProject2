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
		this.custName = custName;
	}

	/**
	 * Gets the customer password.
	 *
	 * @return the customer password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the customer password.
	 *
	 * @param password
	 *            the new customer password
	 */
	public void setPassword(String password) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + (int) (custId ^ (custId >>> 32));
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (coupons == null) {
			if (other.coupons != null)
				return false;
		} else if (!coupons.equals(other.coupons))
			return false;
		if (custId != other.custId)
			return false;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
