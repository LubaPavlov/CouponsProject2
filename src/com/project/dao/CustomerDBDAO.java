package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.CouponSystemException;
import com.project.main.CouponSystem;

/*CustomerDBDAO class implements CustomerDAO interface.
Providing methods to INSERT, UPDATE, DELETE 
and SELECT data from and to MySQL database.*/

public class CustomerDBDAO implements CustomerDAO {

	private static final String TABLE_NAME = "customer";
	private Connection con = null;

	// A method to CREATE a new Customer in the Customer table with auto-generated ID
	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL INSERT prepare statement
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO " + TABLE_NAME + "(custName, password)" + " VALUES (?, ?)");
				// Set parameters in the INSERT query
				stat.setString(1, customer.getCustName());
				stat.setString(2, customer.getPassword());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new customer " + customer.getCustName() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot create customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	// A method to DELETE Customer from the Customer table and Customer_Coupon table
	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL DELETE prepare statement
				PreparedStatement stat = con.prepareStatement(
						"DELETE FROM " + TABLE_NAME + "WHERE Cust_id=?; DELETE FROM Customer_Coupon WHERE Cust_id=?");
				// Set parameter in the DELETE query
				stat.setLong(1, customer.getCustId());
				stat.setLong(2, customer.getCustId());
				System.out.println("Executing: " + stat.toString());
				// Execute update statement
				int rowsDeleted = stat.executeUpdate();
				if (rowsDeleted > 0) {
					System.out.println("A customer " + customer.getCustName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot remove customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	// A method to UPDATE Customer details in the Customer table except Company name and ID
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
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
			if (rowsUpdated > 0) {
				System.out.println(customer + " has been updated successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot update customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	// A method to GET a Customer object by Customer ID from Customer table
	@Override
	public Customer getCustomer(long custId) throws CouponSystemException {
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

			throw new CouponSystemException("Cannot found customer with ID " + custId + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return customer;
	}

	// A method to GET a collection of all customers
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
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

			throw new CouponSystemException("SQL Error" + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return customers;
	}

	// A method to GET a collection of all coupons of specific customer
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException {
		// Create a new Coupons collection
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con.prepareStatement("SELECT * FROM coupon.coupon where couponId in "
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

			throw new CouponSystemException("SQL Error" + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return coupons;
	}
	//A method to LOGIN
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		boolean succeeded = false;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			// Create MySQL SELECT prepare statement
			PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE custName=?");
			// Set parameter in the SELECT query
			stat.setString(1, custName);
			// Execute query statement
			ResultSet rows = stat.executeQuery();
			while (rows.next()) {
				// Set fields
				String myName = rows.getString("custName");
				String myPassword = rows.getString("password");
				if (myName.equals(custName) && myPassword.equals(password)) {
					System.out.println("Success!");
					return succeeded = true;
				} else {
					System.out.println("Failure!");
					return succeeded = false;
				}
			}
		} catch (SQLException e) {

			throw new CouponSystemException("An error occured" + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}

		return succeeded;
	}
	//A method to GET a Customer ID by Customer Name  
	@Override
	public long getCustomerId(String custName) throws CouponSystemException {
		long custId = 0;
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				// Create MySQL SELECT prepare statement
				PreparedStatement stat = con
						.prepareStatement("SELECT custId FROM " + TABLE_NAME + " WHERE custName =?");
				// Set parameter in the SELECT query
				stat.setString(1, custName);
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				ResultSet rows = stat.executeQuery();					
				// Set field
				custId = rows.getLong("custId");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot found customer ID " + custName + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
		return custId;
	}

	@Override
	//A method to ADD a coupon to a customer, insert a new data to a customer_coupon join table
	public void addCouponToCustomer(Coupon coupon, Customer customer) throws CouponSystemException {
		// Get a connection from the Connection Pool
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// Create MySQL INSERT prepare statement
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO customer_coupon " + "(custId, couponId)" + " VALUES (?, ?)");
				// Set parameter in the INSERT query
				stat.setLong(1, customer.getCustId());
				stat.setLong(2, coupon.getCouponId());
				System.out.println("Executing: " + stat.toString());
				// Execute query statement
				int rowsInserted = stat.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("A new coupon " + coupon.getTitle() + " has been added successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			throw new CouponSystemException("Cannot ad coupon : " + coupon.getTitle() + ". " + e.getMessage());

		} finally {
			// Release connection
			releaseConnection(con);
		}
	}

	private Connection getConnection() throws SQLException {

		return CouponSystem.getConnectionPool().getConnection();
	}

	private void releaseConnection(Connection con) {

		CouponSystem.getConnectionPool().free(con);
	}

}
