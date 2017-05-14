package com.project.facade;

import java.util.Collection;

import javax.activation.CommandMap;

import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
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
		// TODO Auto-generated method stub
		return null;
	}

	public void createCompany(Company company) throws DAOException {
		companyDAO.createCompany(company);
	}

	public void removeComapny(Company company) throws DAOException {
		companyDAO.removeCompany(company);

	}
	
	public void updateCompany (Company company) throws DAOException{
		companyDAO.updateCompany(company);
	}
	
	public Collection<Company> getAllCompanies() throws DAOException{
		if (companyDAO == null) {
			return null;
		}
		return companyDAO.getAllCompanies();
	}

	public Company getCompany(String compName) throws DAOException{
		long compId = companyDAO.getCompanyId(compName);
		Company company = new Company();
		company = companyDAO.getCompany(compId);
		return company;
	}
	
		public Company getCompany(long compId) throws DAOException{
			return companyDAO.getCompany(compId);
		}


	public void updateCustomer(Customer customer) throws DAOException{
		customerDAO.updateCustomer (customer);
	}
	

	public Collection<Customer> getAllCustomers() throws DAOException {
		Collection<Customer> allCustomers = customerDAO.getAllCustomers();
		return allCustomers;
	}
	

	public void createCustomer(Customer customer) throws DAOException{
		customerDAO.createCustomer(customer);
	}


	public void removeCustomer(Customer customer)  throws DAOException{
		customerDAO.removeCustomer(customer);
	}
	

	public Customer getCustomerId(String custName) throws DAOException{
		long custId = customerDAO.getCustomerId(custName);
		Customer customer = new Customer();
		customer = customerDAO.getCustomer(custId);
		return customer;
	}
	

		public Customer getCustomer(long customerId) throws DAOException{
			return customerDAO.getCustomer(customerId);
		}
}
