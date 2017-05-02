package com.project.main;

import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CustomerDAO;
import com.project.dao.CustomerDBDAO;
import com.project.exceptions.CustomerUpdateException;
import com.project.exceptions.DAOException;

public class MyTestJDBC {

	public static void main(String[] args) throws DAOException, CustomerUpdateException {

		CustomerDAO cDao = new CustomerDBDAO();
		//Customer customer = new Customer();
		//cDao.getCustomer(10);
		System.out.println(cDao.getCustomer(10));
		//cDao.removeCustomer(customer);
		// cDao.createCustomer(customer);
		// cDao.updateCustomer(customer1);

		// CompanyDAO compDao = new CompanyDBDAO();
		//
		// Company company = new Company(1, "Next", "123456","next@gmail.com");
		//
		// compDao.createCompany(company);

	}

}
