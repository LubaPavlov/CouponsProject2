package com.project.facade;

import java.util.Collection;

import com.project.beans.*;
import com.project.dao.*;
import com.project.exceptions.CouponSystemException;
import com.project.main.ClientType;

public class AdminFacade implements CouponClientFacade {

	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	public AdminFacade() {
	}

	public AdminFacade(CustomerDAO customerDAO, CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.customerDAO = customerDAO;
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	//Login to the Coupon System with ADMIN Client Type
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
	
	// A method to CREATE a new Company in the Company table
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

	public void removeComapny(Company company) throws CouponSystemException {
		
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

	public Collection<Company> getAllCompanies() throws CouponSystemException {
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	public Company getCompanyById(long compId) throws CouponSystemException {
		return companyDAO.getCompany(compId);
	}

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

	public Customer getCustomer(long custId) throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getAllCustomers();
	}

	public Customer getCustomerById(Long custId) throws CouponSystemException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}
}
