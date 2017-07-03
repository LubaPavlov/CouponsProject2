/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.util.Collection;

import com.project.beans.*;
import com.project.exceptions.CouponSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface CouponDAO.
 */
public interface CouponDAO {

	/**
	 * Creates a new coupon.
	 *
	 * @param Coupon
	 *            the coupon to be created
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void createCoupon(Coupon Coupon) throws CouponSystemException;

	/**
	 * Deletes the coupon.
	 *
	 * @param Coupon
	 *            the coupon to be deleted
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void removeCoupon(Coupon Coupon) throws CouponSystemException;

	/**
	 * Update coupon.
	 *
	 * @param Coupon
	 *            the coupon to be updated
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public void updateCoupon(Coupon Coupon) throws CouponSystemException;

	/**
	 * Gets the coupon.
	 *
	 * @param coupon_id
	 *            the coupon id
	 * @return the coupon
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Coupon getCoupon(long coupon_id) throws CouponSystemException;

	/**
	 * Gets the list of all coupons.
	 *
	 * @return the list of all coupons
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * Gets the coupon by type.
	 *
	 * @param couponType
	 *            the coupon type
	 * @return the coupon by provided type
	 * @throws CouponSystemException
	 *             the DAO exception
	 */
	public Coupon getCouponByType(CouponType couponType) throws CouponSystemException;

}
