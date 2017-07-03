package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.exceptions.CouponSystemException;
import com.project.main.CouponSystem;

/*CouponDBDAO class implements CouponDAO interface.
Providing methods to INSERT, UPDATE, DELETE 
and SELECT data from and to MySQL database.*/

public class CouponDBDAO implements CouponDAO {

	private static final String TABLE_NAME = "coupon";
	private Connection con = null;

	// A method to CREATE a new Coupon in the Coupon table with auto-generated ID
	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL INSERT prepare statement
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				// Set parameters in the INSERT query
				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new coupon " + coupon.getTitle() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("Cannot create coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/*A method to REMOVE Coupon from the Coupon table 
	and Join tables: company_coupon, customer_coupon*/
	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create SQL DELETE prepare statement
				PreparedStatement stat = con.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE couponId=?; "
						+ "DELETE FROM company_coupon WHERE couponId=?; "
						+ "DELETE FROM customer_coupon WHERE couponId=?;");
				// Set parameter in the DELETE query
				stat.setLong(1, coupon.getCouponId());
				stat.setLong(2, coupon.getCouponId());
				stat.setLong(3, coupon.getCouponId());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsDeleted = stat.executeUpdate();
				if (rowsDeleted > 0) {
					System.out.println(coupon + " has been deteled successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("Cannot delete coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	// A method to UPDATE Coupon details in the Coupon table
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create SQL UPDATE prepare statement
				PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME + " SET"
						+ "(title, startDate, endDate, amount, type, message, price, image) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				// Set parameters in the UPDATE query
				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setInt(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println(coupon + " has been updated successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("Cannot update coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	// A method to GET a Coupon object by Coupon ID from Coupon table
	@Override
	public Coupon getCoupon(long couponId) throws CouponSystemException {
		Coupon coupon = null;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE couponId=? ");
				// Set parameter in the SELECT query
				stat.setLong(1, couponId);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new Coupon object
					coupon = new Coupon();
					// Set object's fields
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
					coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
				}
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("Cannot found a coupon : " + couponId + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupon;
	}

	// A method to GET a collection of all coupons
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		// Create a new Coupons collection
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new coupon object
					Coupon coupon = new Coupon();
					// Set object's fields
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
					coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
					// Add the object to the Coupons Collection
					coupons.add(coupon);
				}
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("SQL Error" + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupons;
	}

	// A method to GET a Coupon object by a coupon type
	@Override
	public Coupon getCouponByType(CouponType couponType) throws CouponSystemException {
		Coupon coupon = null;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE type=? ");
				// Set parameter in the SELECT query
				stat.setString(1, couponType.toString());
				System.out.println("Executing: " + stat.toString());
				// Execute query
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new Coupon object
					coupon = new Coupon();
					// Set object's fields
					coupon.setCouponId(rows.getLong("couponId"));
					coupon.setTitle(rows.getString("title"));
					coupon.setStartDate(rows.getDate("startDate"));
					coupon.setEndDate(rows.getDate("endDate"));
					coupon.setAmount(rows.getInt("amount"));
					coupon.setType(CouponType.valueOf(rows.getString("type")));
					coupon.setMessage(rows.getString("message"));
					coupon.setPrice(rows.getDouble("price"));
					coupon.setImage(rows.getString("image"));
				}
			}
		} catch (SQLException e) {
			
			throw new CouponSystemException("Cannot found a coupon : " + couponType + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}

		return coupon;
	}

	private Connection getConnection() throws SQLException {

		return CouponSystem.getConnectionPool().getConnection();
	}

	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}

}
