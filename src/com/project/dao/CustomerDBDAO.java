package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import com.project.beans.Coupon;
import com.project.beans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	private static final String TABLE_NAME = "customer";
	private Connection con = null;
	private String dbName = "coupon";

	@Override
	public void createCustomer(Customer customer) {
		/// 1. get a connection (from pool)
		try {
			con = getConnection();

			// 2. create sql insert
			PreparedStatement stat = con.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)");
			stat.setLong(1, customer.getCust_id());
			stat.setString(2, customer.getCustName());
			stat.setString(3, customer.getPassword());

			System.out.println("Executing: " + stat.toString());
			stat.executeUpdate();

		} catch (SQLException e) {
			// TODO: deal with exception
			e.printStackTrace();
		}

		finally {
			// 3. release connection
			releaseConnection(con);
		}

	}

	@Override
	public void removeCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer getCustomer(long cust_id) {
		// TODO Auto-generated method stub
		return null;
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
		return DriverManager.getConnection("jdbc:mysql://localhost/coupon", "root", "123123");
	}

	private void releaseConnection(Connection con) {
		// We currently are closing the connections. Later we
		// will move to a better solution using a connection pool
		// that Assaf will provide
		try {
			con.close();
		} catch (SQLException e) {
			// We don't care
			e.printStackTrace();
		}
	}

}
