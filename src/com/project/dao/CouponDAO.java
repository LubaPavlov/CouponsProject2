/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.util.Collection;

import com.project.beans.*;
import com.project.exceptions.DAOException;

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
	 * @throws DAOException If something fails at database level.
	 */
	public void createCoupon(Coupon Coupon) throws DAOException;

	/**
	 * Deletes the coupon.
	 *
	 * @param Coupon
	 *            the coupon to be deleted
	 * @throws DAOException If something fails at database level.
	 */
	public void removeCoupon(Coupon Coupon) throws DAOException;

	/**
	 * Update coupon.
	 *
	 * @param Coupon
	 *            the coupon to be updated
	 * @throws DAOException If something fails at database level.
	 */
	public void updateCoupon(Coupon Coupon) throws DAOException;

	/**
	 * Gets the coupon.
	 *
	 * @param coupon_id
	 *            the coupon id
	 * @return the coupon
	 * @throws DAOException If something fails at database level.
	 */
	public Coupon getCoupon(long couponId) throws DAOException;

	/**
	 * Gets the list of all coupons.
	 *
	 * @return the list of all coupons
	 * @throws DAOException If something fails at database level.
	 */
	public Collection<Coupon> getAllCoupons() throws DAOException;

	/**
	 * Gets the coupon by type.
	 *
	 * @param couponType
	 *            the coupon type
	 * @return the coupon by provided type
	 * @throws DAOException If something fails at database level.
	 */
	public Coupon getCouponByType(CouponType couponType) throws DAOException;

}
