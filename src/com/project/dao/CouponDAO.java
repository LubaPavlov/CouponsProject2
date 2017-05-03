package com.project.dao;

import java.util.Collection;

import com.project.beans.*;

public interface CouponDAO {
	
	public void createCoupon(Coupon Coupon);

	public void removeCoupon(Coupon Coupon);

	public void updateCoupon(Coupon Coupon);

	public Coupon getCoupon(long coupon_id);

	public Collection<Coupon> getAllCoupons();

	public Coupon getCouponByType(CouponType couponType);

	public Collection<Coupon> getOldCoupons();
	
	public void setCompanyId();
}
