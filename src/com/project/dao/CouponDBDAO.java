package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.project.beans.Coupon;
import com.project.beans.CouponType;

public class CouponDBDAO implements CouponDAO{
	
	private static final String TABLE_NAME = "coupon";
	private Connection con = null;
	private String dbName = "coupon";
	
	@Override
	public void createCoupon(Coupon coupon) {
		/// 1. get a connection (from pool)
				try {
					con = getConnection();
					if (con != null) {
						System.out.println("Connected");
						// 2. create sql insert
						PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(title, startDate, endDate, amount, type, message, price, image) "
								+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
						
						stat.setString (1, coupon.getTitle());
						stat.setDate (2, coupon.getStartDate());
						stat.setDate (3, coupon.getEndDate());
						stat.setInt (4, coupon.getAmount());
						stat.setString (5, coupon.getType().toString());
						stat.setString (6, coupon.getMessage());
						stat.setDouble (7, coupon.getPrice());
						stat.setString (8, coupon.getImage());

						System.out.println("Executing: " + stat.toString());
						// stat.executeUpdate();
						int rowsInserted = stat.executeUpdate();
						if (rowsInserted > 0) {
							System.out.println("A new coupon " + coupon.getTitle() + " has been created successfully");
						} else
							System.out.println("An Error Has Occurred. Check if entered data is correct.");

					}
				} catch (SQLException e) {
					// TODO: deal with exception
					System.out.println("Cannot create coupon : " + coupon.getTitle() + ". " + e.getMessage());
					
				} finally {
					// 3. release connection
					releaseConnection(con);
				}
			}

	@Override
	public void removeCoupon(Coupon Coupon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCoupon(Coupon Coupon) {
		// TODO Auto-generated method stub
		
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
		// TODO: maybe we should catch the exception here
		// and close the program. It is too severe
		// String url = "jdbc:mysql://localhost:3306/coupon", "root",
		// "password";
		return DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, "root", "123123");
	}

	private void releaseConnection(Connection con) {
		// We currently are closing the connections. Later we
		// will move to a better solution using a connection pool
		// that Assaf will provide
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setCompanyId() {
		// TODO Auto-generated method stub
		
	}

}
