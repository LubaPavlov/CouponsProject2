package com.project.beans;

import java.io.Serializable;
import java.util.Collection;

import javax.sql.rowset.spi.TransactionalWriter;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cust_id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
	}

	public Customer(long cust_id, String custName, String password) {

		this.cust_id = cust_id;
		this.custName = custName;
		this.password = password;
	}

	public long getCust_id() {
		return cust_id;
	}

	public void setCust_id(long cust_id) {
		checkCust_id(cust_id);
		this.cust_id = cust_id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		checkCustName(custName);
		this.custName = custName;
	}

	public String getPassword() {
		checkPassword(password);
		return password;
	}

	public void setPassword(String password) {
		checkPassword(password);
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [cust_id=" + cust_id + ", custName=" + custName + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

	private void checkCust_id(long cust_id) {
		if (cust_id == 0){
			throw new IllegalArgumentException("ID can't be zero");
		}
	}

	private void checkCustName(String custName) {
		if (custName == null || custName.isEmpty()) {
			throw new NullPointerException("Name can't be null");
		}
	}

	private void checkPassword(String password) {
		if (password == null||password.isEmpty()) {
			throw new NullPointerException("Password can't be null or empty");
		}
	}

}
