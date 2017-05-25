package com.project.main;

import java.sql.SQLException;

import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.dao.*;
import com.project.exceptions.DAOException;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CouponClientFacade;
import com.project.facade.CustomerFacade;

public class CouponSystem {

	private static CouponSystem couponSystemInstance = new CouponSystem();
    public	AdminFacade AdminFacade = new AdminFacade();
	private static ConnectionPool pool;
	private static String dbName = "coupon2";

	public static synchronized CouponSystem getInstance() {
		if (couponSystemInstance == null) {
			// create a CouponSystem only once
			couponSystemInstance = new CouponSystem();
		}
		return couponSystemInstance;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws DAOException {

        // Admin login
		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {

			couponSystemInstance.AdminFacade.login(name, password, ClientType.ADMIN);

		}

		// Company login
		if (clientType == ClientType.COMPANY) {

		}

		// Customer login
		if (clientType == ClientType.CUSTOMER) {


		}
		
		return null;
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
		pool.closeAllConnections();
		System.out.println("system shutdown.");
		System.exit(0);
	}
}
