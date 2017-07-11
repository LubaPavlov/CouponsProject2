package com.project.main;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import org.w3c.dom.CDATASection;

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
import com.project.exceptions.FacadeException;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CouponClientFacade;
import com.project.facade.CustomerFacade;

public class MyTestJDBC {

	public static void main(String[] args) throws LoginException, FacadeException {

		CouponSystem newsys = CouponSystem.getInstance();

		//Admin facade 
		
		AdminFacade facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);		
		
		//Customer functions
		
		//System.out.println(facade.getAllCustomers());
		//System.out.println(facade.getCustomerById(7));
		//facade.createCustomer(new Customer());
        //facade.updateCustomer(new Customer("Mikix","5555555"));
		//facade.updateCustomer(facade.getCustomerById(1));
        //facade.removeCustomer(facade.getCustomerById(33));
		
		//Companay functions
		
		//facade.createCompany(new Company("lenovo", "123456", "lenovo@gmail.com"));
		//facade.createCompany(new Company("lenovo", "123456", "lenovo@gmail.com"));
		//System.out.println(facade.getAllCompanies());
		//System.out.println(facade.getCompanyById(11));
		//facade.removeCompany(facade.getCompanyById(16));
		//facade.updateCompany(facade.getCompanyById(1));



        //Customer facade
		
		/*
		 *CustomerFacade facade = (CustomerFacade) newsys.login("Leo","123123", ClientType.CUSTOMER);
		 *System.out.println(facade.getAllPurchasedCoupons());
		 *System.out.println(facade.getAllPurchasedCouponsByPrice(100));
		 *System.out.println(facade.getAllPurchasedCouponsByType(CouponType.SPORTS));
		 *facade.purchaseCoupon(facade.getCoupon(13));
        */
		
        //Company facade				 

		/*
		 * CompanyFacade facade = (CompanyFacade) newsys.login("Next", "123",ClientType.COMPANY);
		 * 
		 * Coupon coupon1 = new Coupon();
		 * coupon1.setTitle("10% Off next discount99");
		 * coupon1.setStartDate(java.sql.Date.valueOf("2018-02-04"));
		 * coupon1.setEndDate(java.sql.Date.valueOf("2018-03-04"));
		 * coupon1.setAmount(20); coupon1.setType(CouponType.SPORTS);
		 * coupon1.setPrice(33);
		 * facade.createCoupon(coupon1)
		 */

	}
}
