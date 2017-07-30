/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerFacade.
 */
public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	private Customer customer = new Customer();

	/**
	 * Instantiates a new customer facade.
	 */
	public CustomerFacade() {
	}

	/**
	 * Instantiates a new customer facade.
	 *
	 * @param customerDAO
	 *            the customer DAO
	 * @param couponDAO
	 *            the coupon DAO
	 * @param customer
	 *            the customer
	 */
	public CustomerFacade(CustomerDAO customerDAO, CouponDAO couponDAO, Customer customer) {
		this.customerDAO = customerDAO;
		this.couponDAO = couponDAO;
		this.customer = customer;
	}

	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	// return this customer
	public Customer getCustomer() {
		return this.customer;
	}

	/**
	 * Sets the customer.
	 *
	 * @param customer
	 *            the new customer
	 */
	// set the Facade for this customer
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Login to the Coupon System with Customer Client Type.
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client
	 * @param clientType
	 *            the client type
	 * @return the coupon client facade
	 * @throws LoginException
	 * @throws FacadeException
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException, FacadeException {

		CustomerFacade custfacade = new CustomerFacade();
		try {
			if (!customerDAO.login(name, password)) {
				throw new FacadeException("Wrong user name or password");
			}
			this.customer = customerDAO.getCustomerByName(name);
		} catch (DAOException e) {
			throw new FacadeException("Error user not found");
		}
		System.out.println("Login succeed");
		custfacade.setCustomer(this.customer);
		return custfacade;

	}

	/**
	 * A method to Purchase coupon for logged in customer.
	 *
	 * @param coupon
	 *            the coupon object
	 * @return true, if successful
	 * @throws FacadeException
	 */

	// TODO need to check if coupon is not expired
	public boolean purchaseCoupon(Coupon coupon) throws FacadeException {
		Coupon purachseCoupon;
		try {
			purachseCoupon = getCoupon(coupon.getCouponId());
		} catch (Exception e) {
			throw new FacadeException("Coupon not found");
		}

		Collection<Coupon> myCoupons = null;
		try {
			myCoupons = customerDAO.getCoupons(this.customer);
		} catch (DAOException e1) {
			throw new FacadeException(e1.getLocalizedMessage());
		}

		for (Coupon purachse : myCoupons) {
			// check that this customer doesn't already purchased this coupon
			if (purachse.getCouponId() == purachseCoupon.getCouponId()) {
				System.out.println("You already purchased this coupon");
			}
		}
		// check if at least one coupon exists
		if (purachseCoupon.getAmount() <= 0) {
			System.out.println("No coupons left");
		}

		try {
			// add coupon to customer
			customerDAO.addCouponToCustomer(purachseCoupon, this.customer);
			purachseCoupon.setAmount(purachseCoupon.getAmount() - 1);
			// update coupon's amount
			couponDAO.updateCoupon(purachseCoupon);
			return true;
		} catch (DAOException e) {
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	/**
	 * Gets the all purchased coupons of the logged in customer
	 *
	 * @return the all purchased coupons
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws FacadeException {
		try {
			return customerDAO.getCoupons(this.customer);
		} catch (DAOException e) {
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

	/**
	 * Gets the all purchased coupons by type.
	 *
	 * @param couponType
	 *            the coupon type
	 * @return the collection of all purchased coupons by type
	 * @throws FacadeException
	 */
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

	/**
	 * Gets the all purchased coupons by price.
	 *
	 * @param price
	 * @return the collection of all purchased coupons by price
	 * @throws FacadeException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws FacadeException {

		Collection<Coupon> myCouponsByPrice;
		try {
			myCouponsByPrice = customerDAO.getCoupons(this.customer);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}

		for (Iterator<Coupon> iterator = myCouponsByPrice.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getPrice() > price) {
				iterator.remove();
			}
		}
		return myCouponsByPrice;
	}

	/**
	 * Gets the coupon by coupon ID.
	 *
	 * @param couponId
	 * @return the coupon object
	 * @throws FacadeException
	 */
	public Coupon getCoupon(long couponId) throws FacadeException {

		try {
			return couponDAO.getCoupon(couponId);
		} catch (DAOException e) {
			throw new FacadeException(e.getLocalizedMessage());
		}
	}

}
