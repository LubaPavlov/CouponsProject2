/*
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.beans;

import java.io.Serializable;
import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Coupon.
 */
public class Coupon implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long couponId;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	/**
	 * Default constructor Instantiates a new coupon.
	 */
	// A default constructor 
	public Coupon() {
	}

	/**
	 * Instantiates a new coupon.
	 *
	 * @param title
	 *            the coupon title
	 * @param startDate
	 *            the coupon start date
	 * @param endDate
	 *            the coupon end date
	 * @param amount
	 *            the coupon amount
	 * @param type
	 *            the coupon type
	 * @param price
	 *            the coupon price
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, double price) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.price = price;
	}

	/**
	 * Gets the coupon id.
	 *
	 * @return the coupon id
	 */
	public long getCouponId() {
		return couponId;
	}

	/**
	 * Sets the coupon id.
	 *
	 * @param couponId
	 *            the new coupon id
	 */
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	/**
	 * Gets the coupon title.
	 *
	 * @return the coupon title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the coupon title.
	 *
	 * @param title
	 *            the new coupon title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the coupon start date.
	 *
	 * @return the coupon start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the coupon start date.
	 *
	 * @param startDate
	 *            the new coupon start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the coupon end date.
	 *
	 * @return the coupon end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the coupon end date.
	 *
	 * @param endDate
	 *            the new coupon end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the coupon amount.
	 *
	 * @return the coupon amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the coupon amount.
	 *
	 * @param amount
	 *            the new coupon amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the coupon type.
	 *
	 * @return the coupon type
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * Sets the coupon type.
	 *
	 * @param type
	 *            the new coupon type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * Gets the coupon message.
	 *
	 * @return the coupon message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the coupon message.
	 *
	 * @param message
	 *            the new coupon message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the coupon price.
	 *
	 * @return the coupon price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the coupon price.
	 *
	 * @param price
	 *            the new coupon price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the coupon image.
	 *
	 * @return the coupon image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the coupon image.
	 *
	 * @param image
	 *            the new coupon image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
