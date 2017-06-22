package imports.d20170427coupProjDafnaWeiss.dao;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
/**
 * Coupon DAO Interface: Coupon Methods using the DataBase
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public interface CouponDAO {
	/**
	 * Create a new Coupon in the Coupon table in the Database
	 * @param coupon The given coupon to be created
	 * @throws MyException SQL exception
	 */
	public void createCoupon(Coupon coupon)throws MyException;
	/**
	 * Remove a Coupon from Database
	 * @param coupon The given coupon to be removed
	 * @throws MyException SQL exception
	 */
	public void removeCoupon(Coupon coupon)throws MyException;
	/**
	 * Update a Coupon in the Coupon table in the Database
	 * @param coupon The given coupon to be updated
	 * @throws MyException SQL exception
	 */
	public void updateCoupon(Coupon coupon)throws MyException;
	/**
	 * Get a coupon from the Coupon table in the Database BY ID
	 * @param id The coupon's ID in the Database that we want to get
	 * @return Coupon The coupon of the given ID
	 * @throws MyException SQL exception 
	 */
	public Coupon getCoupon(long id)throws MyException;
	/**
	 * Get all the Coupons from the Coupon table in the Database 
	 * @return A collection of all the coupons from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getAllCoupons()throws MyException;
	/**
	 * Get all the Coupons of the given TYPE from the Coupon table in the Database
	 * @param couponType The given coupon Type to get the coupons according to it
	 * @return A collection of all the coupons of the given type from the Database
	 * @throws MyException SQL exception 
	 */
	public Collection<Coupon> getCouponsByType(CouponType couponType)throws MyException;
}
