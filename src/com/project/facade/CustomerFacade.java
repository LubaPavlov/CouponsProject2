package com.project.facade;

import java.util.Collection;
import java.util.Iterator;

import javax.security.auth.login.LoginException;

import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CouponDBDAO;
import com.project.dao.CustomerDAO;
import com.project.dao.CustomerDBDAO;
import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
import com.project.main.ClientType;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	private Customer customer = new Customer();

	public CustomerFacade() {
	}

	public CustomerFacade(CustomerDAO customerDAO, CouponDAO couponDAO, Customer customer) {
		this.customerDAO = customerDAO;
		this.couponDAO = couponDAO;
		this.customer = customer;
	}

	// return this customer
	public Customer getCustomer() {
		return this.customer;
	}

	// set the Facade for this customer
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// Login
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginException {

		if (clientType != ClientType.CUSTOMER) {
			throw new IndexOutOfBoundsException("Clinet type is not a customer");
		}

		if (!customerDAO.login(name, password)) {
			throw new IndexOutOfBoundsException("Wrong user name or password");
		}

		CustomerFacade custfacade = new CustomerFacade();
		this.customer = customerDAO.getCustomerByName(name);
		custfacade.setCustomer(this.customer);
		return custfacade;
	}

	// Purchase
	// 1 - check if at least one coupon exists
	// 2 - check that this customer doesn't already purchased this coupon
	// 3 - add coupon to customer
	// 4 - update amount in DB

	// need to check if coupon is not expired

	public boolean purchaseCoupon(Coupon coupon) throws FacadeException {

		Coupon purachseCoupon;
		try {
			purachseCoupon = getCoupon(coupon.getCouponId());
		} catch (Exception e) {
			throw new FacadeException("Coupon not found");
		}

		Collection<Coupon> myCoupons = customerDAO.getCoupons(this.customer);

		for (Coupon purachse : myCoupons) {

			if (purachse.getCouponId() == purachseCoupon.getCouponId()) {
				throw new FacadeException("You already purchased this coupon");
			}
		}

		if (purachseCoupon.getAmount() <= 0) {
			throw new FacadeException("No coupons left");
		}

		try {
			customerDAO.addCouponToCustomer(purachseCoupon, this.customer);
			purachseCoupon.setAmount(purachseCoupon.getAmount() - 1);
			couponDAO.updateCoupon(purachseCoupon);
			return true;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	public Collection<Coupon> getAllPurchasedCoupons() throws FacadeException {

		return customerDAO.getCoupons(this.customer);
	}

	// get all purchased coupons by coupon type
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws FacadeException {
		Collection<Coupon> myCouponsByType = getAllPurchasedCoupons();
		for (Iterator<Coupon> iterator = myCouponsByType.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getType() != couponType) {
				iterator.remove();
			}
		}
		return myCouponsByType;
	}

	// get all purchased coupons by price (< price)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws FacadeException {
		Collection<Coupon> myCouponsByPrice = customerDAO.getCoupons(this.customer);
		for (Iterator<Coupon> iterator = myCouponsByPrice.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getPrice() > price) {
				iterator.remove();
			}
		}
		return myCouponsByPrice;
	}

	public Coupon getCoupon(long couponId) throws FacadeException {

		return couponDAO.getCoupon(couponId);
	}

}
