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
import com.project.exceptions.CouponSystemException;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CouponClientFacade;
import com.project.facade.CustomerFacade;

public class MyTestJDBC {

	public static void main(String[] args) throws CouponSystemException, LoginException {

	    CouponSystem newsys = CouponSystem.getInstance();
		
/*	    AdminFacade facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
		facade.createCompany(new Company("Golf123", "123456","gold@gmail.com"));
		System.out.println(facade.getAllCompanies());
		Company comptorem = facade.getCompanyById(3);
		facade.removeCompany(comptorem);*/
		
	  /*  CustomerFacade facade =  (CustomerFacade) newsys.login("Leo", "123123", ClientType.CUSTOMER);
	    facade.getAllPurchasedCoupons();
	    Collection<Coupon> couponim = facade.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);
	    facade.purchaseCoupon(new CouponDBDAO().getCoupon(2));*/
	    
	   
	    
	    CompanyFacade facade = (CompanyFacade) newsys.login("Next", "123", ClientType.COMPANY);
	    Coupon coupon3 = new Coupon();
/*		coupon1.setTitle("10% Off next discount99");
		coupon1.setStartDate(java.sql.Date.valueOf("2018-02-04"));
		coupon1.setEndDate(java.sql.Date.valueOf("2018-03-04"));
		coupon1.setAmount(20);
		coupon1.setType(CouponType.SPORTS);
		coupon1.setPrice(33);	*/
		try {
			facade.createCoupon(coupon3);
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
		}	

	}
}
