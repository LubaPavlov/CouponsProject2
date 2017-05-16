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

		if (clientType == ClientType.ADMIN) {
			AdminFacade adminFacade = new AdminFacade(customerDAO, companyDAO, couponDAO);
			return adminFacade;
		}
		/*
		 * if (clientType == ClientType.COMPANY){ Long compId =
		 * companyDao.login(name, password); if(compId != null && compId != 0){
		 * Company company = new Company(); company.setCompId(compId);
		 * company.setCompName(name); company.setPassword(password);
		 * CompanyFacade companyFacade = new CompanyFacade(company); return
		 * companyFacade; } }
		 */
		if (clientType == ClientType.CUSTOMER) {
			Long custId = customerDAO.getCustomerId(name);
			
			if (custId != null && custId != 0) {
				Customer customer = new Customer();
				customer.setCustId(custId);
				customer.setCustName(name);
				customer.setPassword(password);
				CustomerFacade customerFacade = new CustomerFacade();
				return customerFacade;
			}
		}
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
		pool.closeAllConnections();
		System.out.println("system shutdown.");
		System.exit(0);
	}
}
