package com.project.dao;

import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.CouponSystemException;

public interface CustomerDAO {

	public void createCustomer(Customer customer) throws CouponSystemException;

	public void removeCustomer(Customer customer) throws CouponSystemException;

	public void updateCustomer(Customer customer) throws CouponSystemException;

	public Customer getCustomer(long cust_id) throws CouponSystemException;

	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;

	public boolean login(String custName, String password) throws CouponSystemException;

	public long getCustomerId(String custName) throws CouponSystemException;

	public void addCouponToCustomer(Coupon coupon, Customer customer) throws CouponSystemException;

}
