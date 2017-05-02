package com.project.beans;

import java.io.Serializable;
import java.util.Collection;

public class Company implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long compId;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	public Company() {
	}
	
	
	public Company(long compId, String compName, String password, String email) {
		this.compId = compId;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}


	public long getCompId() {
		return compId;
	}
	public void setCompId(long compId) {
		this.compId = compId;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "Company [comp_id=" + compId + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}

}
