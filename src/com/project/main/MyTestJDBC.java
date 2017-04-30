package com.project.main;


import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CustomerDAO;
import com.project.dao.CustomerDBDAO;
import com.project.exceptions.DAOException;

public class MyTestJDBC {

	public static void main(String[] args) throws DAOException {

      CustomerDAO cDao = new CustomerDBDAO();
      Customer customer = new Customer(7, "test", "123456");
      cDao.getCustomer(2);
      //cDao.createCustomer(customer);
      cDao.updateCustomer(cDao.getCustomer(2));
//      CompanyDAO compDao = new CompanyDBDAO();
//		
//		Company company = new Company(1, "Next", "123456","next@gmail.com");
//		
//		compDao.createCompany(company);

	}

	
}
