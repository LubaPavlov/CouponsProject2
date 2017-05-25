package com.project.facade;

import java.util.Collection;

import com.project.beans.*;
import com.project.dao.*;
import com.project.exceptions.DAOException;
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

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		AdminFacade adminFacade = null;
		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {
			 adminFacade = new AdminFacade(customerDAO, companyDAO, couponDAO);
			// return adminFacade;
		}	
		else {
			System.out.println("Not correct user info");
		}
		 return adminFacade;
	}

	public void createCompany(Company company) throws DAOException {
		
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
			throw new DAOException();
		}
	}

	public void removeComapny(Company company) throws DAOException {
		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {

				Collection<Coupon> coupons = company.getCoupons();
				for (Coupon couponToRemove : coupons) {
					couponDAO.removeCoupon(couponToRemove);
				}
				companyDAO.removeCompany(company);
			} else {
				throw new DAOException();
			}
		}
	}

	public void updateCompany(Company company) throws DAOException {

		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyDAO.updateCompany(company);
			} else {
				throw new DAOException();
			}
		}
	}

	public Collection<Company> getAllCompanies() throws DAOException {
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	public Company getCompanyById(long compId) throws DAOException {
		return companyDAO.getCompany(compId);
	}

	public void createCustomer(Customer customer) throws DAOException {

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
			throw new DAOException();
		}

	}

	public void removeCustomer(Customer customer) throws DAOException {
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

	public void updateCustomer(Customer customer) throws DAOException {
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

	public Customer getCustomer(long custId) throws DAOException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}

	public Collection<Customer> getAllCustomers() throws DAOException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getAllCustomers();
	}

	public Customer getCustomerById(Long custId) throws DAOException {
		if (customerDAO == null) {
			return null;
		}
		return customerDAO.getCustomer(custId);
	}
}
