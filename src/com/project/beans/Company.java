/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.beans;

import java.io.Serializable;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class Company.
 */
public class Company implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long compId;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	/**
	 * Default constructor Instantiates a new company.
	 */
	public Company() {
	}
		
	/**
	 * Instantiates a new company.
	 *
	 * @param compName
	 *            the company name
	 * @param password
	 *            the company password
	 * @param email
	 *            the company email
	 */
	public Company(String compName, String password, String email) {
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public long getCompId() {
		return compId;
	}
	
	/**
	 * Sets the company id.
	 *
	 * @param compId
	 *            the new company id
	 */
	public void setCompId(long compId) {
		this.compId = compId;
	}
	
	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompName() {
		return compName;
	}
	
	/**
	 * Sets the company name.
	 *
	 * @param compName
	 *            the new company name
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	/**
	 * Gets the company password.
	 *
	 * @return the company password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the company password.
	 *
	 * @param password
	 *            the new company password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the company email.
	 *
	 * @return the company email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the company email.
	 *
	 * @param email
	 *            the new company email
	 */
	public void setEmail(String email) {
		this.email = email;
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
		return "Company [comp_id=" + compId + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (compId ^ (compId >>> 32));
		result = prime * result + ((compName == null) ? 0 : compName.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (compId != other.compId)
			return false;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		return true;
	}

}
