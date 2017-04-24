package com.project.dao;

import java.util.Collection;

import com.project.beans.Company;
import com.project.beans.Coupon;

//
//CompanyDBDAO class implements CompanyDAO interface.
//Provide's methods to insert and retrieve data from and to the DB.
//

public class CompanyDBDAO implements CompanyDAO{

	@Override
	public void createCompany(Company Company) {
		// TODO add validation should be not possible to add a company with same name twice
		
	}

	@Override
	public void removeCompany(Company Company) {
		// TODO  data should be removed in join table as well
		
	}

	@Override
	public void updateCompany(Company Company) {
		// TODO company name cannot be updated
		
	}

	@Override
	public Company getCompany(long comp_id) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Company> getAllCompanies() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Coupon> getCoupons() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(String compName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
