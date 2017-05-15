package com.project.main;

import java.sql.SQLException;

import com.project.dao.*;
import com.project.facade.CouponClientFacade;

public class CouponSystem {

	private static CouponSystem couponSystemInstance = new CouponSystem();
	private CompanyDAO companyDao;
	private CouponDAO couponDao;
	private CustomerDAO customerDao;
	private static ConnectionPool pool;
	private static String dbName = "coupon";

	public static synchronized CouponSystem getInstance() {
		if (couponSystemInstance == null) {
			// create a CouponSystem only once
			couponSystemInstance = new CouponSystem();
		}
		return couponSystemInstance;
	}
	
	public CouponClientFacade login(String name, String password, ClientType clientType){
		return null;		
	}

	// returns DAO
	public CustomerDAO getCustomerDao() {
		return this.customerDao;
	}

	// returns DAO
	public CompanyDAO getCompanyDao() {
		return this.companyDao;
	}

	// returns DAO
	public CouponDAO getCouponDao() {
		return this.couponDao;
	}

	static {
		String url = "jdbc:mysql://localhost:3306/" + dbName;

		String username = "root";
		String password = "123123";
		try {
			pool = new ConnectionPool(url, username, password);
		} catch (SQLException e) {
			System.out.println("Cannot create connection pool. " + "reason: " + e.getMessage());
			System.exit(1);
		}
	}

	public static ConnectionPool getConnectionPool() {
		return pool;
	}

	public void shutdown() throws SQLException {
		System.out.println("system shutdown requested.");
		pool.closeAllConnections();
		System.out.println("system gracefully shutdown.");
		System.exit(0);
	}
}
