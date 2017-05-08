package com.project.main;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CompanyDBDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CouponDBDAO;
import com.project.dao.CustomerDAO;
import com.project.dao.CustomerDBDAO;
import com.project.exceptions.DAOException;

public class MyTestJDBC {

	public static void main(String[] args) throws DAOException{

//		CustomerDAO cDao = new CustomerDBDAO();
//		cDao.login("Leo", "123123");
//		Customer customer = new Customer("Leo","123123");
//		cDao.createCustomer(customer);
//		Customer customer2 = new Customer("Lilah","123456");
//		cDao.createCustomer(customer2);
//  	Customer customer3 = new Customer("Idan","123456789");
		//cDao.createCustomer(customer3);
		//cDao.getCustomer(2);
//		System.out.println(cDao.getCustomer(1));
//		cDao.login("Fox","123456");
		//cDao.updateCustomer(customer3);

//		CompanyDAO compDao = new CompanyDBDAO();
//		compDao.login("Fox", "123456");
//		Company company = new Company("Fox", "123456","fox@gmail.com");
//		compDao.createCompany(company);

		CouponDAO couponDao = new CouponDBDAO();
		Coupon couponnew = new Coupon();
		couponDao.createCoupon(couponnew);
		couponnew.setTitle("dddddd");
		couponDao.updateCoupon(couponnew);
//		Coupon coupon4 = new Coupon();
//		coupon4.setTitle("10% Off");
//		coupon4.setStartDate(java.sql.Date.valueOf("2018-02-04"));
//		coupon4.setEndDate(java.sql.Date.valueOf("2018-03-04"));
//		coupon4.setAmount(20);
//		coupon4.setType(CouponType.SPORTS);
//		coupon4.setPrice(33);
//		couponDao.createCoupon(coupon4);
		
		
		
	}

}
