/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
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
import com.project.exceptions.DAOException;
import com.project.main.CouponSystem;

// TODO: Auto-generated Javadoc
/**
 * The Class CouponDBDAO implements CouponDAO interface. Providing methods to
 * INSERT, UPDATE, DELETE and SELECT data from and to MySQL database.
 */
public class CouponDBDAO implements CouponDAO {

	private static final String TABLE_NAME = "coupon";
	private Connection con = null;

	/**
	 * A method to CREATE a new Coupon in the Coupon table with auto-generated
	 * ID
	 *
	 * @param Coupon
	 *            the coupon to be created
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public void createCoupon(Coupon coupon) throws DAOException {
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
				if (rowsInserted == 0) {
					throw new DAOException("Create coupon failed, no rows affected.");
				}
			}
		} catch (SQLException e) {

			throw new DAOException(e);

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to REMOVE Coupon from the Coupon table and Join tables:
	 * company_coupon, customer_coupon
	 *
	 * @param Coupon
	 *            the coupon to be deleted
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws DAOException {
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
				if (rowsDeleted == 0) {
					throw new DAOException("Delete coupon failed, no rows affected.");
				}
			}
		} catch (SQLException e) {

			throw new DAOException(e);

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to UPDATE Coupon details in the Coupon table
	 *
	 * @param Coupon
	 *            the coupon to be updated
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create SQL UPDATE prepare statement
				PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME
						+ " SET endDate=?, amount=?, message=?, price=?, image=? WHERE couponId=?");
				// Set parameters in the UPDATE query
				stat.setDate(1, coupon.getEndDate());
				stat.setInt(2, coupon.getAmount());
				stat.setString(3, coupon.getMessage());
				stat.setDouble(4, coupon.getPrice());
				stat.setString(5, coupon.getImage());
				stat.setLong(6, coupon.getCouponId());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted == 0) {
					throw new DAOException("Update coupon failed, no rows affected.");
				}
			}
		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e.getCause());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to GET a Coupon object by Coupon ID from Coupon table
	 *
	 * @param coupon_id
	 *            the coupon id
	 * @return the coupon
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public Coupon getCoupon(long couponId) throws DAOException {
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

			throw new DAOException(e);

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupon;
	}

	/**
	 * A method to GET a collection of all coupons
	 *
	 * @return the list of all coupons
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws DAOException {
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

			throw new DAOException(e);

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupons;
	}

	/**
	 * A method to GET a Coupon object by a coupon type
	 *
	 * @param couponType
	 *            the coupon type
	 * @return the coupon by provided type
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public Coupon getCouponByType(CouponType couponType) throws DAOException {
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

			throw new DAOException(e);

		} finally {
			// Release connection
			releaseConnection(con);
		}

		return coupon;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection to pool
	 * @throws SQLException
	 *             the SQL exception
	 */
	private Connection getConnection() throws SQLException {

		return CouponSystem.getConnectionPool().getConnection();
	}

	/**
	 * Release connection.
	 *
	 * @param con
	 *            the connection to pool
	 */
	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}

}
