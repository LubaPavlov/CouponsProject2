package com.project.main;

import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import org.omg.Messaging.SyncScopeHelper;

import com.project.beans.Customer;
import com.project.dao.*;
import com.project.exceptions.DAOException;
import com.project.facade.*;

public class CouponSystem {
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

	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException {

		CouponClientFacade facade = null;
		switch (clientType) {

		case ADMIN:
			AdminFacade adminFacade = new AdminFacade();
			facade = adminFacade.login(name, password, clientType);
			break;

		case COMPANY:
			CompanyFacade companyFacade = new CompanyFacade();
			facade = companyFacade.login(name, password, clientType);

			break;
		case CUSTOMER:
			CustomerFacade customerFacade = new CustomerFacade();
			facade = customerFacade.login(name, password, clientType);
			break;
		}

		if (null == facade) {
			throw new LoginException("clientType = " + clientType + "name =" + name);
		}
		return facade;

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
