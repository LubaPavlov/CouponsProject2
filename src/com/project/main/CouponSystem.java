/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.main;

import java.sql.SQLException;
import javax.security.auth.login.LoginException;
import com.project.dao.*;
import com.project.exceptions.CouponSystemException;
import com.project.facade.*;

// TODO: Auto-generated Javadoc
/**
 * The Class CouponSystem.
 */
public class CouponSystem {
	
	/** The coupon system instance. */
	//private CouponClientFacade CouponClientFacade;
	private static CouponSystem couponSystemInstance = new CouponSystem();
	private static ConnectionPool pool;
	private static String dbName = "coupon";

	/**
	 * Create the single instance of CouponSystem.
	 *
	 * @return single instance of CouponSystem
	 */
	public static synchronized CouponSystem getInstance() {
		if (couponSystemInstance == null) {
			// Create a CouponSystem only once
			couponSystemInstance = new CouponSystem();
		}
		return couponSystemInstance;
	}
	
	/**
	 * Instantiates a new coupon system and start Daily Task Thread
	 */
	private CouponSystem() {
		// Start Daily Task Thread
		/*DailyCouponExpirationTask dailyTask = new DailyCouponExpirationTask();
		Thread dailyTaskThread = new Thread(dailyTask);
		dailyTaskThread.start();
		System.out.println("Starting Daily Task");*/
	}

	/**
	 * A method to Login to the CouponSystem return the correct facade by the client TYPE
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client 
	 * @param clientType
	 *            the client type
	 * @return the coupon client facade
	 * @throws LoginException
	 *             the login exception
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException, CouponSystemException {

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

		if (facade == null) {
			throw new LoginException("clientType = " + clientType + "name =" + name);
		}
		return facade;

	}

	static {
		String url = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";

		String username = "root";
		String password = "123123";
		try {
			pool = new ConnectionPool(url, username, password);
		} catch (SQLException e) {
			System.out.println("Cannot create connection pool. " + "reason: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Gets the connection pool.
	 *
	 * @return the connection pool
	 */
	public static ConnectionPool getConnectionPool() {
		return pool;
	}

	/**
	 * Shutdown.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void shutdown() throws SQLException {
		pool.closeAllConnections();
		System.out.println("System shutdown.");
		System.exit(0);
	}
}
