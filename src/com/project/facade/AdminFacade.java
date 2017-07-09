/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.facade;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.project.beans.*;
import com.project.dao.*;
import com.project.exceptions.CouponSystemException;
import com.project.main.ClientType;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminFacade. Manage the Company's and Customer's methods by the
 * ADMIN client
 */
public class AdminFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	/**
	 * Instantiates a new ADMIN facade.
	 */
	public AdminFacade() {
	}

	/**
	 * Instantiates a new ADMIN facade.
	 *
	 * @param customerDAO
	 *            the customer DAO
	 * @param companyDAO
	 *            the company DAO
	 * @param couponDAO
	 *            the coupon DAO
	 */
	public AdminFacade(CustomerDAO customerDAO, CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.customerDAO = customerDAO;
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	/**
	 * Login to the Coupon System with ADMIN Client Type
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client
	 * @param clientType
	 *            the client type ADMIN
	 * @return the coupon client facade
	 * @throws LoginException
	 *             the login exception
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		if (clientType != ClientType.ADMIN) {
			System.out.println("Client type is not admin");
			throw new IndexOutOfBoundsException("Clinet type is not admin");
		}

		if (!name.equals("admin") || !password.equals("1234")) {
			System.out.println("Login failed. Not correct username or password.");
			throw new IndexOutOfBoundsException("Login failed. Not correct username or password.");
		}

		System.out.println("Login succeed");
		AdminFacade facade = new AdminFacade();
		return facade;
	}

	/**
	 * A method to CREATE a new Company in the Company table with auto-generated
	 * ID
	 *
	 * @param company
	 *            the company
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void createCompany(Company company) throws CouponSystemException {

		boolean companyExist = false;
		// Create new List of all existing companies
		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {

			if (existingCompany.getCompName() == company.getCompName()) {
				companyExist = true;
				System.out.println("Company witht this name already exists in the system");
				break;
			}
		}
		if (!companyExist)
			companyDAO.createCompany(company);
		else {
			throw new CouponSystemException();
		}
	}

	/**
	 * A method to REMOVE Company from the Company table, invokes couponDAO
	 * method removeComany
	 *
	 * @param company
	 *            the company object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void removeCompany(Company company) throws CouponSystemException {

		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName().equals(company.getCompName())) {

				Collection<Coupon> coupons = company.getCoupons();
				if (coupons != null) {
					for (Coupon couponToRemove : coupons) {
						couponDAO.removeCoupon(couponToRemove);
					}
				} else {
					throw new CouponSystemException();
				}
			}
		}
		companyDAO.removeCompany(company);
	}

	/**
	 * A method to UPDATE Company details in the Company table except Company
	 * name and ID
	 *
	 * @param company
	 *            the company object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void updateCompany(Company company) throws CouponSystemException {

		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyDAO.updateCompany(company);
			} else {
				throw new CouponSystemException();
			}
		}
	}

	/**
	 * A method to GET a collections of all companies from Company table
	 *
	 * @return the collection of all companies
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	/**
	 * A method to GET a Company by Company ID from Company table
	 *
	 * @param compId
	 *            the company id
	 * @return the company object by id
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Company getCompanyById(long compId) throws CouponSystemException {
		return companyDAO.getCompany(compId);
	}

	/**
	 * A method to CREATE a new Customer in the Customer table with
	 * auto-generated ID
	 *
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {

		boolean customerExist = false;
		// Create new List of all existing customers
		Collection<Customer> customers = getAllCustomers();

		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustName() == customer.getCustName()) {
				customerExist = true;
				break;
			}
		}
		if (!customerExist)
			customerDAO.createCustomer(customer);
		else {
			throw new CouponSystemException();
		}
	}

	/**
	 * A method to DELETE Customer from the Customer table and Customer_Coupon
	 * table
	 *
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException {
		// Create new List of all existing customers
		Collection<Customer> customers = getAllCustomers();
		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustName() == customer.getCustName()) {
				customerDAO.removeCustomer(customer);
			} else {
				System.out.println("Cannot found customer " + customer.getCustName());
			}
		}
	}

	/**
	 * A method to UPDATE Customer details in the Customer table except Company
	 * name and ID
	 *
	 * @param customer
	 *            the customer object
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		// Create new List of all existing companies
		Collection<Customer> customers = getAllCustomers();

		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustName() == customer.getCustName()) {
				customerDAO.updateCustomer(customer);
			} else {
				System.out.println("Cannot found customer" + customer.getCustName());
			}
		}
	}

	/**
	 * A method to GET a Customer object by Customer ID from Customer table
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Customer getCustomer(long custId) throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}

	/**
	 * A method to GET a collection of all customers
	 *
	 * @return the collection of all customers
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getAllCustomers();
	}

	/**
	 * A method to GET a Customer object by Customer ID from Customer table
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer object by id
	 * @throws CouponSystemException
	 *             the coupon system exception
	 */
	public Customer getCustomerById(Long custId) throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}
}
