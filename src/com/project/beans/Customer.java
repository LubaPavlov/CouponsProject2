package com.project.beans;

import java.io.Serializable;
import java.util.Collection;
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long custId;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
	}

	public Customer(String custName, String password) {

		this.custName = custName;
		this.password = password;
	}
	public Customer(long custId, String custName) {

		this.custId = custId;
		this.custName = custName;

	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		checkCustId(custId);
		this.custId = custId;
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
		return "Customer [custId=" + custId + ", custName=" + custName + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

	private void checkCustId(long custId) {
		if (custId == 0){
			throw new IllegalArgumentException("ID can't be zero");
		}
	}

	private void checkCustName(String custName) {
		if (custName == null) {
			throw new NullPointerException("Name can't be null");
		}
	}

	private void checkPassword(String password) {
		if (password == null||password.isEmpty()) {
			throw new NullPointerException("Password can't be null or empty");
		}
	}

}
