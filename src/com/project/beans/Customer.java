package com.project.beans;

import java.util.Collection;

public class Customer {
	
	private long cust_id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	public Customer() {
	}
	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
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
	

}
