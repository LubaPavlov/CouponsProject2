package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import com.project.beans.*;
import com.project.exceptions.DAOException;
//
//CustomerDBDAO class implements CustomerDAO interface.
//Providing methods to insert, update and select data from and to the DB.
//
public class CustomerDBDAO implements CustomerDAO {

	private static final String TABLE_NAME = "customer";
	private Connection con = null;
	private String dbName = "coupon";

	@Override
	public void createCustomer(Customer customer) throws DAOException {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. create sql insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)");
				stat.setLong(1, customer.getCustId());
				stat.setString(2, customer.getCustName());
				stat.setString(3, customer.getPassword());

				System.out.println("Executing: " + stat.toString());
				// stat.executeUpdate();
				int rowsInserted = stat.executeUpdate();

				if (rowsInserted > 0) {
					System.out.println("A new customer " + customer.getCustName() + " has been created successfully");
				} else
					System.out.println("An Error Has Occurred. Check if entered data is correct.");
			}
		} catch (SQLException e) {
			// TODO: deal with exception
			System.out.println("Cannot create customer : " + customer.getCustName() + ". " + e.getMessage());
			
		} finally {
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public void removeCustomer(Customer customer) {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();
			if (con != null) {
				System.out.println("Connected");
				// 2. create sql insert
				PreparedStatement stat = con
						// .prepareStatement("DELETE FROM " + TABLE_NAME + "
						// WHERE Cust_id=?; DELETE FROM Customer_Coupon WHERE Cust_id=?");
						.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE custId=?");
				stat.setLong(1, customer.getCustId());
				// stat.setLong(2, customer.getCustId());

				System.out.println("Executing: " + stat.toString());
				// stat.executeUpdate();
				int rowsDeleted = stat.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("A customer " + customer.getCustName() + " has been deleted successfully");
				} else
					System.out.println("An Error Has Occurred.");
			}
		} catch (SQLException e) {

			System.out.println("Cannot remove customer : " + customer.getCustName() + ". " + e.getMessage());
			
		} finally {
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();
			// 2. create sql insert
			PreparedStatement stat = con
					.prepareStatement("UPDATE " + TABLE_NAME + " SET custName=?, password=? WHERE custName=?");
			stat.setString(1, customer.getCustName());
			stat.setString(2, customer.getPassword());
			stat.setString(3, customer.getCustName());

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
			// 3. release connection
			releaseConnection(con);
		}
	}

	@Override
	public Customer getCustomer(long custId) throws DAOException {
		Customer customer = new Customer();
		/// 1. get a connection (from pool)
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
			// 3. release connection
			releaseConnection(con);
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getCustomerId(String custName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCouponToCustomer(Coupon coupon, Customer customer) {
		// TODO Auto-generated method stub

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

}
