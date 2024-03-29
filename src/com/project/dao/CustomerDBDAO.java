/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.DAOException;
import com.project.main.CouponSystem;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerDBDAO implements CustomerDAO interface. Providing methods
 * to INSERT, UPDATE, DELETE and SELECT data from and to MySQL database
 */
public class CustomerDBDAO implements CustomerDAO {

	private static final String TABLE_NAME = "customer";
	private Connection con = null;

	/**
	 * A method to CREATE a new Customer in the Customer table with
	 * auto-generated ID
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public void createCustomer(Customer customer) throws DAOException {

		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create MySQL INSERT prepare statement
			PreparedStatement stat = con
					.prepareStatement("INSERT INTO " + TABLE_NAME + "(custName, password)" + " VALUES (?, ?)");
			// Set parameters in the INSERT query
			stat.setString(1, customer.getCustName());
			stat.setString(2, customer.getPassword());
			System.out.println("Executing: " + stat.toString());
			// Execute update statement
			int rowsInserted = stat.executeUpdate();
			if (rowsInserted == 0) {
				throw new DAOException("Create customer failed, no rows affected.");
			}

		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to DELETE Customer from the Customer table and Customer_Coupon
	 * table
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public void removeCustomer(Customer customer) throws DAOException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL DELETE prepare statement
				PreparedStatement stat = con.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE custId=?");
				// Set parameter in the DELETE query
				stat.setLong(1, customer.getCustId());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsDeleted = stat.executeUpdate();
				if (rowsDeleted == 0) {
					throw new DAOException("Delete customer failed, no rows affected.");
				}
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to UPDATE Customer details in the Customer table except Company
	 * name and ID
	 *
	 * @param customer
	 *            the customer object
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create SQL UPDATE prepare statement
			PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME + " SET password=? WHERE custName=?");
			// Set parameters in the UPDATE query
			stat.setString(1, customer.getPassword());
			stat.setString(2, customer.getCustName());
			System.out.println("Executing: " + stat.toString());
			// Execute update statement
			int rowsUpdated = stat.executeUpdate();
			if (rowsUpdated == 0) {
				throw new DAOException("Update customer failed, no rows affected.");
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to GET a Customer object by Customer ID from Customer table
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer object
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public Customer getCustomer(long custId) throws DAOException {
		Customer customer = null;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE custID=?");
				// Set parameter in the SELECT query
				stat.setLong(1, custId);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new Customer object
					customer = new Customer();
					// Set object's fields
					customer.setCustId(rows.getLong("custId"));
					customer.setCustName(rows.getString("custName"));
					customer.setPassword(rows.getString("password"));
				}
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return customer;
	}

	/**
	 * A method to GET a collection of all customers
	 *
	 * @return the collection of all customers
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws DAOException {
		// Create a new Customers collection
		ArrayList<Customer> customers = new ArrayList<Customer>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new customer object
					Customer customer = new Customer();
					// Set object's fields
					customer.setCustId(rows.getLong("custId"));
					customer.setCustName(rows.getString("custName"));
					customer.setPassword(rows.getString("password"));
					// Add the object to the Customers Collection
					customers.add(customer);
				}
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return customers;
	}

	/**
	 * A method to GET a collection of all coupons of specific customer
	 *
	 * @param customer
	 *            the customer object
	 * @return the coupons collection
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException {
		// Create a new Coupons collection
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM coupon where couponId in "
						+ "(select couponId from customer_coupon where custId= " + customer.getCustId() + ")");
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

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupons;
	}

	/**
	 * A method to LOGIN
	 *
	 * @param custName
	 *            the customer's name
	 * @param password
	 *            the customer's password
	 * @return true, if successful
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public boolean login(String custName, String password) throws DAOException {
		boolean succeeded = false;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create MySQL SELECT prepare statement
			PreparedStatement stat = con.prepareStatement("SELECT password FROM " + TABLE_NAME + " WHERE custName=?");
			// Set parameter in the SELECT query
			stat.setString(1, custName);
			// Execute query statement
			ResultSet rows = stat.executeQuery();
			while (rows.next()) {
				String myPassword = rows.getString("password");
				if (myPassword.equals(password)) {
					succeeded = true;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Exception occurred! Message: " + e.getMessage());
		} finally {
			// Release connection
			releaseConnection(con);
		}
		return succeeded;
	}

	/**
	 * A method to GET a Customer ID by Customer Name
	 *
	 * @param custName
	 *            the customer's name
	 * @return the customer's id
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public long getCustomerId(String custName) throws DAOException {
		long custId = 0;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create MySQL SELECT prepare statement
			PreparedStatement stat = con.prepareStatement("SELECT custId FROM " + TABLE_NAME + " WHERE custName =?");
			// Set parameter in the SELECT query
			stat.setString(1, custName);
			System.out.println("Executing: " + stat.toString());
			// Execute query statement
			ResultSet rows = stat.executeQuery();
			// Set field
			while (rows.next()) {
				custId = rows.getLong("custId");
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return custId;
	}

	/**
	 * A method to ADD a coupon to a customer, insert a new data to a
	 * customer_coupon join table
	 *
	 * @param coupon
	 *            the coupon object
	 * @param customer
	 *            the customer object
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public void addCouponToCustomer(Coupon coupon, Customer customer) throws DAOException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL INSERT prepare statement
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO customer_coupon " + "(custId, couponId)" + " VALUES (?, ?)");
				// Set parameter in the INSERT query
				stat.setLong(1, customer.getCustId());
				stat.setLong(2, coupon.getCouponId());
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted == 0) {
					throw new DAOException("Add coupon to customer failed, no rows affected.");
				}
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	/**
	 * A method to GET the customer object by name.
	 *
	 * @param name
	 *            the name of the customer
	 * @return the customer object by name
	 * @throws DAOException 
	 *            If something fails at database level.
	 */
	@Override
	public Customer getCustomerByName(String name) throws DAOException {
		Customer customer = null;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE custName=?");
				// Set parameter in the SELECT query
				stat.setString(1, name);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					// Create a new Customer object
					customer = new Customer();
					// Set object's fields
					customer.setCustId(rows.getLong("custId"));
					customer.setCustName(rows.getString("custName"));
					customer.setPassword(rows.getString("password"));
					return customer;
				}
			}
		} catch (SQLException e) {

			throw new DAOException("Exception occurred! Message: " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return customer;
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
