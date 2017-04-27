package com.project.beans;

import java.util.Collection;

public class Company {
	
	private long comp_id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	public Company() {
	}
	
	
	public Company(long comp_id, String compName, String password, String email) {
		this.comp_id = comp_id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}


	public long getComp_id() {
		return comp_id;
	}
	public void setComp_id(long comp_id) {
		this.comp_id = comp_id;
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
		return "Company [comp_id=" + comp_id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}

}
