package com.project.dao;

import java.util.Collection;

import com.project.beans.Coupon;
import com.project.beans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer(long cust_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getCustomerId(String custName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCouponToCustomer(Coupon coupon, Customer customer) {
		// TODO Auto-generated method stub
		
	}



}
