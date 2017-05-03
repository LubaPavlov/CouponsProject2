package com.project.main;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CouponDBDAO;
import com.project.exceptions.DAOException;

public class MyTestJDBC {

	public static void main(String[] args) throws DAOException{

		//CustomerDAO cDao = new CustomerDBDAO();
		//Customer customer = new Customer();
		//cDao.getCustomer(10);
		//System.out.println(cDao.getCustomer(10));
		//cDao.removeCustomer(customer);
		// cDao.createCustomer(customer);
		// cDao.updateCustomer(customer1);

		CompanyDAO compDao = new CompanyDBDAO();
		Company company = new Company(2, "Nike", "123123","nike@gmail.com");
		compDao.createCompany(company);

//		CouponDAO couponDao = new CouponDBDAO();
//		Coupon coupon4 = new Coupon();
//		coupon4.setTitle("10% Off");
//		coupon4.setStartDate(java.sql.Date.valueOf("2018-02-04"));
//		coupon4.setEndDate(java.sql.Date.valueOf("2018-03-04"));
//		coupon4.setAmount(20);
//		coupon4.setType(CouponType.SPORTS);
//		coupon4.setPrice(33);
//		coupon4.setCompId(1);
//		couponDao.createCoupon(coupon4);
	}

}
