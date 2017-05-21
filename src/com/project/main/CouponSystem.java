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
	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;
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

		// login of Admin
		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {
			AdminFacade adminFacade = new AdminFacade(customerDAO, companyDAO, couponDAO);
			return adminFacade;
		}
		System.out.println("Login Failed");

		// login of Company
		if (clientType == ClientType.COMPANY) {
			CompanyFacade companyFacade  = new CompanyFacade(companyDAO,couponDAO);
			return companyFacade;
		}

		// login of Customer
		if (clientType == ClientType.CUSTOMER) {
			
			CustomerFacade customerFacade  = new CustomerFacade(customerDAO);
            return customerFacade;
		}
		return null;
	}

	// returns DAO
	public CustomerDAO getCustomerDAO() {
		return this.customerDAO;
	}

	// returns DAO
	public CompanyDAO getCompanyDAO() {
		return this.companyDAO;
	}

	// returns DAO
	public CouponDAO getCouponDAO() {
		return this.couponDAO;
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
