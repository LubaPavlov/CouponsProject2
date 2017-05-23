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
	private CouponClientFacade couponClienFacade;
	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	private static ConnectionPool pool;
	private static String dbName = "coupon";

	public static synchronized CouponSystem getInstance() {
		if (couponSystemInstance == null) {
			// create a CouponSystem only once
			couponSystemInstance = new CouponSystem();
		}
		return couponSystemInstance;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws DAOException {

		// Admin login - לפנות לפסד שיפעיל את הפונקציה של לוגין שתפעיל את
		// הפנקמיה בדטה בייס
		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {

			couponSystemInstance.couponClienFacade.login(name, password, clientType);

		}

		// Company login
		if (clientType == ClientType.COMPANY) {

			couponSystemInstance.couponClienFacade.login(name, password, clientType);
		}

		// Customer login
		if (clientType == ClientType.CUSTOMER) {

			couponSystemInstance.couponClienFacade.login(name, password, clientType);

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
