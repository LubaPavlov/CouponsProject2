package com.project.facade;

import java.util.Collection;
import java.util.Iterator;

import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.beans.Customer;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
import com.project.exceptions.DAOException;
import com.project.main.ClientType;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;
	private Customer customer;

	public CustomerFacade() {
	}

	public CustomerFacade(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		return null;
	}

	// Purchase
	// 1 - check if at least one coupon left
	// 2 - check that this customer doesn't already purchased this coupon
	// 3 - add coupon to customer
	// 4 - update amount in DB
	public void PurchaseCoupon(Coupon coupon) throws DAOException {

		Coupon purachseCoupon = couponDAO.getCoupon(coupon.getCouponId());

		if (purachseCoupon.getAmount() <= 0) {
			System.out.println("No coupons left");
		}
		if (getAllPurchasedCoupons().contains(purachseCoupon)) {
			System.out.println("You already purchased this coupon");
		}

		customerDAO.addCouponToCustomer(purachseCoupon, this.customer);
		purachseCoupon.setAmount(purachseCoupon.getAmount() - 1);
				
		couponDAO.updateCoupon(purachseCoupon);

	}

	public Collection<Coupon> getAllPurchasedCoupons() throws DAOException {
		return customerDAO.getCoupons(this.customer);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws DAOException {
		Collection<Coupon> myCouponsByType = customerDAO.getCoupons(this.customer);
		for (Iterator<Coupon> iterator = myCouponsByType.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getType() != couponType) {
				iterator.remove();
			}
		}
		return myCouponsByType;
	}
	
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws DAOException {
		Collection<Coupon> myCouponsByPrice = customerDAO.getCoupons(this.customer);
		for (Iterator<Coupon> iterator = myCouponsByPrice.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getPrice() > price) {
				iterator.remove();
			}
		}
		return myCouponsByPrice;
	}

}
