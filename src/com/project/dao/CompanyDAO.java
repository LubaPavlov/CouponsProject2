package com.project.dao;

import java.util.Collection;

import com.project.beans.*;

public interface CompanyDAO {
	
	public void createCompany(Company Company);

	public void removeCompany(Company Company);

	public void updateCompany(Company Company);

	public Company getCompany(long comp_id);

	public Collection<Company> getAllCompanies();

	public Collection<Coupon> getCoupons();

	boolean login(String compName, String password);

}
