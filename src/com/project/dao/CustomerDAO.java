package com.project.dao;

import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.DAOException;

public interface CustomerDAO {

	public void createCustomer(Customer customer) throws DAOException;

	public void removeCustomer(Customer customer) throws DAOException;

	public void updateCustomer(Customer customer) throws DAOException;

	public Customer getCustomer(long cust_id) throws DAOException;

	public Collection<Customer> getAllCustomers() throws DAOException;

	public Collection<Coupon> getCoupons(Customer customer) throws DAOException;

	boolean login(String custName, String password) throws DAOException;

	public long getCustomerId(String custName) throws DAOException;

	void addCouponToCustomer(Coupon coupon, Customer customer);

	//public void addCouponToCustomer(Coupon coupon, Customer customer) throws DAOException;
}
