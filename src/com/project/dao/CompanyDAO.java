package com.project.dao;

import java.util.Collection;

import com.project.beans.*;
import com.project.exceptions.DAOException;

public interface CompanyDAO {
	
	public void createCompany(Company Company) throws DAOException;

	public void removeCompany(Company Company) throws DAOException;

	public void updateCompany(Company Company)throws DAOException;

	public Company getCompany(long comp_id) throws DAOException;

	public Collection<Company> getAllCompanies() throws DAOException;

	public Collection<Coupon> getCoupons() throws DAOException;

	boolean login(String compName, String password) throws DAOException;

}
