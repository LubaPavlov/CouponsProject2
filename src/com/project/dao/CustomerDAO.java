package com.project.dao;

import java.util.Collection;

import com.project.beans.*;

public interface CustomerDAO {

	public void createCustomer(Customer customer);

	public void removeCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public Customer getCustomer(long cust_id);

	public Collection<Customer> getAllCustomers();

	public Collection<Coupon> getCoupons(Customer customer);

	boolean login(String custName, String password);

	public long getCustomerId(String custName);
	
	public void addCouponToCustomer(Coupon coupon, Customer customer);
}
