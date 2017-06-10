package com.project.main;

import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import org.omg.Messaging.SyncScopeHelper;

import com.project.beans.Customer;
import com.project.dao.*;
import com.project.exceptions.DAOException;
import com.project.facade.*;

public class CouponSystem {
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();
	private CouponClientFacade CouponClientFacade;
	private static CouponSystem couponSystemInstance = new CouponSystem();
	private static ConnectionPool pool;
	private static String dbName = "coupon";

	public static synchronized CouponSystem getInstance() {
		if (couponSystemInstance == null) {
			// create a CouponSystem only once
			couponSystemInstance = new CouponSystem();
		}
		return couponSystemInstance;
	}

	private CouponSystem() {
		// Start Daily Task Thread
	}

	public CouponClientFacade loginAsCustomer(String name, String password, ClientType clientType) throws LoginException {

		// Customer login
		if (clientType == ClientType.CUSTOMER) {
			// try to login
			try {
				if (customerDAO.login(name, password)) {
					long custId = customerDAO.getCustomerId(name);
					// construct a customer with those params
					Customer customer = new Customer();
					customer.setCustId(custId);
					customer.setCustName(name);
					customer.setPassword(password);
					// create a CustomerFacade
					CustomerFacade customerFacade = new CustomerFacade(customerDAO,couponDAO);
					// customerFacade need to ref that specific customer  ( 1 per client )
					customerFacade.setCustomer(customer);			
					
					return customerFacade;
				} else {

					throw new LoginException("Error");
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public CouponClientFacade loginAsAdmin(String name, String password, ClientType clientType) throws LoginException {
		// Admin login
		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {
			AdminFacade adminFacade = new AdminFacade(customerDAO, companyDAO, couponDAO);
			return adminFacade;
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
