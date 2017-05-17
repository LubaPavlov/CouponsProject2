package com.project.facade;

import java.util.ArrayList;
import java.util.Collection;
import com.project.beans.*;
import com.project.dao.*;
import com.project.exceptions.DAOException;
import com.project.main.ClientType;

public class AdminFacade implements CouponClientFacade {
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;

	public AdminFacade() {
	}

	public AdminFacade(CustomerDAO customerDAO, CompanyDAO companyDAO, CouponDAO couponDAO) {
		this.customerDAO = customerDAO;
		this.companyDAO = companyDAO;
		this.couponDAO = couponDAO;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		if (name == "admin" && password == "1234" && clientType == ClientType.ADMIN) {
			AdminFacade adminFacade = new AdminFacade(customerDAO, companyDAO, couponDAO);
			return adminFacade;

		} else
			System.out.println("error");
		return null;
	}

	public void createCompany(Company company) throws DAOException {
		boolean companyExist = false;
		// Create new List of all existing companies
		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyExist = true;
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

		boolean companyExist = false;
		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyExist = true;
				break;
			}
		}
		if (companyExist){
		
		Collection<Coupon> coupons = company.getCoupons();
		
		for(Coupon couponToRemove : coupons)
		{

			couponDAO.removeCoupon(couponToRemove);
		}
		
		companyDAO.removeCompany(company);}
		
		else {
			throw new DAOException();
		}

	}

	public void updateCompany(Company company) throws DAOException {

		boolean companyExist = false;
		Collection<Company> companies = getAllCompanies();

		for (Company existingCompany : companies) {
			if (existingCompany.getCompName() == company.getCompName()) {
				companyExist = true;
				break;
			}
		}
		if (!companyExist)
			companyDAO.updateCompany(company);
		else {
			throw new DAOException();
		}

	}

	public Collection<Company> getAllCompanies() throws DAOException {
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	public Company getCompany(String compName) throws DAOException {
		long compId = companyDAO.getCompanyId(compName);
		Company company = new Company();
		company = companyDAO.getCompany(compId);
		return company;
	}

	public Company getCompanyById(long compId) throws DAOException {
		Company company = new Company();
		company = companyDAO.getCompany(compId);
		return company;
	}

	public void createCustomer(Customer customer) throws DAOException {

		boolean customerExist = false;
		// Create new List of all existing companies
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
		boolean customerExist = false;
		// Create new List of all existing companies
		Collection<Customer> customers = getAllCustomers();

		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustName() == customer.getCustName()) {
				customerExist = true;
				break;
			}
		}
		if (!customerExist)
			customerDAO.removeCustomer(customer);
		else {
			throw new DAOException();
		}

	}

	public void updateCustomer(Customer customer) throws DAOException {
		boolean customerExist = false;
		// Create new List of all existing companies
		Collection<Customer> customers = getAllCustomers();

		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustName() == customer.getCustName()) {
				customerExist = true;
				break;
			}
		}
		if (!customerExist)
			customerDAO.updateCustomer(customer);
		else {
			throw new DAOException();
		}
	}

	public Customer getCustomer(long custId) throws DAOException {
		Customer customer = new Customer();
		customer = customerDAO.getCustomer(custId);
		return customer;
	}

	public Collection<Customer> getAllCustomers() throws DAOException {
		Collection<Customer> allCustomers = customerDAO.getAllCustomers();
		return allCustomers;
	}

	public Customer getCustomerById(String custName) throws DAOException {
		long custId = customerDAO.getCustomerId(custName);
		Customer customer = new Customer();
		customer = customerDAO.getCustomer(custId);
		return customer;
	}

}
