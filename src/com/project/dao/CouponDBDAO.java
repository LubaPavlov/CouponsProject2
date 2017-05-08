package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.project.beans.Coupon;
import com.project.beans.CouponType;

public class CouponDBDAO implements CouponDAO {

	private static final String TABLE_NAME = "coupon";
	private Connection con = null;
	private String dbName = "coupon";

	@Override
	public void createCoupon(Coupon coupon) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. Create SQL insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());

				System.out.println("Executing: " + stat.toString());

				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new coupon " + coupon.getTitle() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot create coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCoupon(Coupon coupon) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. Create SQL UPDATE
				PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME + " SET"
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());

				System.out.println("Executing: " + stat.toString());

				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println(coupon + " has been updated successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot update coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}

	}

	@Override
	public Coupon getCoupon(long couponId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getAllCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coupon getCouponByType(CouponType couponType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getOldCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	private Connection getConnection() throws SQLException {

		return DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, "root", "123123");
	}

	private void releaseConnection(Connection con) {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
