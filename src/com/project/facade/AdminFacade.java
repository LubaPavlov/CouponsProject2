/**
 * @author Luba Pavlov
 * @version 1.0, 03.07.2017
 */
package com.project.facade;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.project.beans.*;
import com.project.dao.*;
import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
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
	 * @param companyDAO
	 * @param couponDAO
	 */
	public AdminFacade(CustomerDAO customerDAO, CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.customerDAO = customerDAO;
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	/**
	 * Login to the Coupon System with ADMIN Client Type.
	 *
	 * @param name
	 *            the name of the client
	 * @param password
	 *            the password of the client
	 * @param clientType
	 *            the client type ADMIN
	 * @return the coupon client facade
	 * @throws LoginException
	 * @throws FacadeException
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws LoginException, FacadeException {

		if (!name.equals("admin") || !password.equals("1234")) {
			throw new FacadeException("Login failed. Not correct username or password.");
		}
		
		System.out.println("Login succeed");
		AdminFacade facade = new AdminFacade();
		return facade;
	}

	/**
	 * A method to CREATE a new Company in the Company table with auto-generated
	 * ID.
	 *
	 * @param company
	 *            the company object
	 * @throws FacadeException
	 */
	public void createCompany(Company company) throws FacadeException {
		// Check if company name not null
		if (!(company.getCompName() == null)) {
			// Create new List of all existing companies
			Collection<Company> companies = getAllCompanies();
			// Checking if the Company already exists
			for (Company existingCompany : companies) {
				if ((existingCompany.getCompName().equals(company.getCompName()))) {
					System.out.println("Company " + company.getCompName() + "already exists in the system");
					break;
				}
			}
			// CREATE a new company
			try {
				companyDAO.createCompany(company);
			} catch (DAOException e) {

				throw new FacadeException(e.getMessage());
			}
			System.out.println("Company " + company.getCompName() + " has been created");
		} else
			throw new FacadeException("Not valid request.The company cannot be null");
	}

	/**
	 * A method to REMOVE Company from the Company table, invokes couponDAO
	 * method removeComany.
	 *
	 * @param company
	 *            the company object
	 * @throws FacadeException
	 */
	public void removeCompany(Company company) throws FacadeException {

		if (!(company == null)) {
			// Create new List of all existing companies
			Collection<Company> companies = getAllCompanies();
			// Checking if the Company exists
			for (Company existingCompany : companies) {
				if (existingCompany.getCompName().equals(company.getCompName())) {
					Collection<Coupon> coupons = company.getCoupons();
					// check if company has coupons
					if (coupons != null) {
						for (Coupon couponToRemove : coupons) {
							// Remove coupons of the provided company
							try {
								couponDAO.removeCoupon(couponToRemove);
							} catch (DAOException e) {
								throw new FacadeException(e.getMessage());
							}
							System.out.println("The coupons of the company has been removed");
						}
					}
					// Remove company
					try {
						companyDAO.removeCompany(company);
					} catch (DAOException e) {
						throw new FacadeException(e.getMessage());
					}
					System.out.println("The company " + company.getCompName() + " has been removed");
				}
			}
		} else
			throw new FacadeException("Not valid request.The company cannot be null");
	}

	/**
	 * A method to UPDATE Company details in the Company table except Company
	 * name and ID.
	 *
	 * @param company
	 *            the company object
	 * @throws FacadeException
	 */
	public void updateCompany(Company company) throws FacadeException {
		// Check if company not null
		if (!(company == null)) {
			// Create new List of all existing companies
			Collection<Company> companies = getAllCompanies();
			// Check if company is exists
			for (Company existingCompany : companies) {
				if (!(existingCompany.getCompName().equals(company.getCompName()))) {
					System.out.println("The company " + company.getCompName() + " not found");
				}
			}
			// Update the company
			try {
				companyDAO.updateCompany(company);
			} catch (DAOException e) {
				throw new FacadeException(e.getMessage());
			}
			System.out.println("The company " + company.getCompName() + " has been updated");
		} else
			System.out.println("Not valid request.The company cannot be null");
	}

	/**
	 * A method to GET a collections of all companies from Company table.
	 *
	 * @return the collection of all companies
	 * @throws FacadeException
	 */
	public Collection<Company> getAllCompanies() throws FacadeException {
		try {
			return companyDAO.getAllCompanies();
		} catch (DAOException e) {

			throw new FacadeException(e.getMessage());
		}
	}

	/**
	 * A method to GET a Company by Company ID from Company table.
	 *
	 * @param compId
	 *            the company id
	 * @return the company object by id
	 * @throws FacadeException
	 */
	public Company getCompanyById(long compId) throws FacadeException {
		try {
			return companyDAO.getCompany(compId);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}
	
	/**
	 * A method to GET a Company by Company Name from Company table.
	 *
	 * @param compName
	 * @return the company object by name
	 * @throws FacadeException
	 */
	public long getCompanyIdByName(String compName) throws FacadeException {
		try {
			return companyDAO.getCompanyId(compName);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}


	/**
	 * A method to CREATE a new Customer in the Customer table with
	 * auto-generated ID.
	 *
	 * @param customer
	 *            the customer object
	 * @throws FacadeException
	 */
	public void createCustomer(Customer customer) throws FacadeException {
		// Check if company name not null
		if (!(customer.getCustName() == null)) {
			boolean customerExist = false;
			// Create new List of all existing customers
			Collection<Customer> customers = getAllCustomers();
			// Check if customer already exists in the system, if yes break
			for (Customer existingCustomer : customers) {
				if (existingCustomer.getCustName().equals(customer.getCustName())) {
					customerExist = true;
					System.out.println("The customer " + customer.getCustName() + " already exists in the system");
					break;
				}
			}
			if (!customerExist) {
				// Create a new customer
				try {
					customerDAO.createCustomer(customer);
				} catch (DAOException e) {
					throw new FacadeException(e.getMessage());
				}
				System.out.println("A new customer " + customer.getCustName() + " has been created");
			}
		} else
			System.out.println("Not valid request.The customer cannot be null");
	}

	/**
	 * A method to DELETE Customer from the Customer table and Customer_Coupon
	 * table.
	 *
	 * @param customer
	 *            the customer object
	 * @throws FacadeException
	 */
	public void removeCustomer(Customer customer) throws FacadeException {
		// Check if customer not null
		if (!(customer == null)) {
			// Create new List of all existing customers
			Collection<Customer> customers = getAllCustomers();
			// Check if company exists
			for (Customer existingCustomer : customers) {
				if (existingCustomer.getCustName().equals(customer.getCustName())) {
					// Create collection of all customer's coupons
					Collection<Coupon> coupons = customer.getCoupons();
					// Check if provided customer has coupons
					if (coupons != null) {
						for (Coupon couponToRemove : coupons) {
							// Remove all customer's coupon from join table
							try {
								couponDAO.removeCoupon(couponToRemove);
							} catch (DAOException e) {
								throw new FacadeException(e.getMessage());
							}
							System.out.println("The coupons of the customer has been removed");
						}
					}
					// Remove customer
					try {
						customerDAO.removeCustomer(customer);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						throw new FacadeException(e.getMessage());
					}
					System.out.println("The customer has been removed");
				}
			}
		} else
			throw new FacadeException("Not valid request.The customer cannot be null");
	}

	/**
	 * A method to UPDATE Customer details in the Customer table except Company
	 * name and ID.
	 *
	 * @param customer
	 *            the customer object
	 * @throws FacadeException
	 */
	public void updateCustomer(Customer customer) throws FacadeException {
		// Check if customer name not null
		if (!(customer == null)) {
			// Create new List of all existing customers
			Collection<Customer> customers = getAllCustomers();

			for (Customer existingCustomer : customers) {
				// Check if company exists in the system
				if (existingCustomer.getCustName().equals(customer.getCustName())) {
					try {
						customerDAO.updateCustomer(customer);
					} catch (DAOException e) {
						throw new FacadeException(e.getMessage());
					}
					//System.out.println("Customer " + customer.getCustName() + " has been updated");
				}
			}
		} else
			throw new FacadeException("Not valid request.The company cannot be null");
	}

	/**
	 * A method to GET a Customer object by Customer ID from Customer table.
	 *
	 * @param custId
	 *            the customer id
	 * @return the customer
	 * @throws FacadeException
	 */
	public Customer getCustomerById(long custId) throws FacadeException {
		try {
			return customerDAO.getCustomer(custId);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}
	
	/**
	 * A method to GET a Customer by Customer Name from Customer table.
	 *
	 * @param custName
	 * @return the custoemr object by name
	 * @throws FacadeException
	 */
	public Customer getCustomerByName(String custName) throws FacadeException {
		try {
			return customerDAO.getCustomerByName(custName);
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	/**
	 * A method to GET a collection of all customers.
	 *
	 * @return the collection of all customers
	 * @throws FacadeException
	 */
	public Collection<Customer> getAllCustomers() throws FacadeException {
		try {
			return customerDAO.getAllCustomers();
		} catch (DAOException e) {
			throw new FacadeException(e.getMessage());
		}
	}
}
