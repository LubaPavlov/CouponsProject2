package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.DAOException;

//
//CustomerDBDAO class implements CustomerDAO interface.
//Providing methods to insert, update and select data from and to the Data Base.
//
public class CustomerDBDAO implements CustomerDAO {

	private static final String TABLE_NAME = "customer";
	private Connection con = null;
	private String dbName = "coupon";

	@Override
	public void createCustomer(Customer customer) throws DAOException {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. create SQL INSER
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO " + TABLE_NAME + "(custName, password)" + " VALUES (?, ?)");
				stat.setString(1, customer.getCustName());
				stat.setString(2, customer.getPassword());

				System.out.println("Executing: " + stat.toString());

				int rowsInserted = stat.executeUpdate();

				if (rowsInserted > 0) {
					System.out.println("A new customer " + customer.getCustName() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot create customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCustomer(Customer customer) {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. create SQL DELETE
				PreparedStatement stat = con.prepareStatement(
						"DELETE FROM " + TABLE_NAME + "WHERE Cust_id=?; DELETE FROM Customer_Coupon WHERE Cust_id=?");
				stat.setLong(1, customer.getCustId());
				stat.setLong(2, customer.getCustId());

				System.out.println("Executing: " + stat.toString());

				int rowsDeleted = stat.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("A customer " + customer.getCustName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot remove customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			// 2. Create SQL UPDATE
			PreparedStatement stat = con.prepareStatement("UPDATE " + TABLE_NAME + " SET password=? WHERE custName=?");
			stat.setString(1, customer.getPassword());
			stat.setString(2, customer.getCustName());

			System.out.println("Executing: " + stat.toString());
			// stat.executeUpdate();
			int rowsUpdated = stat.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println(customer + " has been updated successfully");
			} else
				System.out.println("An Error Has Occurred. Check if entered data is correct.");

		} catch (SQLException e) {

			System.out.println("Cannot update customer : " + customer.getCustName() + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
	}

	@Override
	public Customer getCustomer(long custId) throws DAOException {
		Customer customer = new Customer();
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE custID=? ");
				stat.setLong(1, custId);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();

				while (rows.next()) {
					customer.setCustId(rows.getLong("custId"));
					customer.setCustName(rows.getString("custName"));
					customer.setPassword(rows.getString("password"));
				}
			}

		} catch (SQLException e) {

			System.out.println("Cannot found customer with ID " + custId + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList <Customer>();
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();
				while (rows.next()) {
					Customer customer = new Customer();
					customer.setCustId(rows.getLong("custId"));
					customer.setCustName(rows.getString("custName"));
					customer.setPassword(rows.getString("password"));
					customers.add(customer);
				}
			}
		} catch (SQLException e) {

			System.out.println("SQL Error" + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return customers;
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, String password) {
		boolean succeeded = false;
		try {
			con = getConnection();
			// 2. Create SQL insert
			PreparedStatement stat = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE custName=?");
			stat.setString(1, custName);

			ResultSet rows = stat.executeQuery();
			while (rows.next()) {
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

			System.out.println("An error occured" + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}

		return succeeded;
	}

	@Override
	public long getCustomerId(String custName) {
		long custId = 0;
		// 1. Get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				PreparedStatement stat = con
						.prepareStatement("SELECT custId FROM " + TABLE_NAME + " WHERE custName=? ");
				stat.setString(1, custName);
				System.out.println("Executing: " + stat.toString());
				ResultSet rows = stat.executeQuery();

				custId = rows.getLong("custId");
				System.out.println("Customer id is " + custId);

			}
		} catch (SQLException e) {

			System.out.println("Cannot found customer with ID " + custName + ". " + e.getMessage());

		} finally {
			// 3. Release connection
			releaseConnection(con);
		}
		return custId;
	}

	@Override
	public void addCouponToCustomer(Coupon coupon, Customer customer) {
		// 1. Get a connection (from pool)
				try {
					con = getConnection();
					if (con != null) {
						System.out.println("Connected");
						// 2. create SQL INSER
						PreparedStatement stat = con
								.prepareStatement("INSERT INTO customer_coupon " + "(custId, couponId)" + " VALUES (?, ?)");
						stat.setLong(1, customer.getCustId());
						stat.setLong(2, coupon.getCouponId());

						System.out.println("Executing: " + stat.toString());

						int rowsInserted = stat.executeUpdate();

						if (rowsInserted > 0) {
							System.out.println("A new coupon " + coupon.getTitle() + " has been added successfully");
						} else
							System.out.println("An Error Has Occurred.");
					}
				} catch (SQLException e) {

					System.out.println("Cannot ad coupon : " + coupon.getTitle() + ". " + e.getMessage());

				} finally {
					// 3. Release connection
					releaseConnection(con);
				}

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
