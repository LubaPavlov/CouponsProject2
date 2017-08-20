package com.project.main;

import javax.security.auth.login.LoginException;

import com.project.beans.*;
import com.project.exceptions.DAOException;
import com.project.exceptions.FacadeException;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CustomerFacade;

public class MyTestJDBC {

	public static void main(String[] args) throws FacadeException, LoginException, DAOException {

		CouponSystem newsys = CouponSystem.getInstance();

		//Admin facade 
		
	    //	AdminFacade facade = null;
			//facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
			//System.out.println(facade.getAllCustomers());
			//System.out.println(facade.getCustomerById(1));
			//facade.removeCustomer(facade.getCustomerById(1));
			//facade.createCustomer(new Customer("Idan","123123"));	
		
		//Customer functions
		
		//System.out.println(facade.getCustomerById(1));
		//facade.createCustomer(new Customer("Dror","123123"));
        //facade.updateCustomer(new Customer("Mikix333","9999999"));
        //facade.removeCustomer(facade.getCustomerById(1));
		
		//Company functions
		
		//facade.createCompany(new Company("Steven", "54545454", "steven@gmail.com"));
		//System.out.println(facade.getAllCompanies());
		//System.out.println(facade.getCompanyById(11));
			//long compId = facade.getCompanyIdByName("newcomp1");
		//	facade.removeCompany(facade.getCompanyById(compId));
		//facade.updateCompany(new Company("Guess","1111111","guess@gmail.com"));

		
		
        //Customer facade
		
		
/*		 try {
			CustomerFacade facade = (CustomerFacade) newsys.login("Idan","123123", ClientType.CUSTOMER);
			// System.out.println(facade.getAllPurchasedCoupons());
     		// facade.purchaseCoupon(facade.getCoupon(4));
		} catch (LoginException | FacadeException | DAOException e) {
			System.out.println(e.getMessage());
		}
		*/
//		 System.out.println(facade.getAllPurchasedCouponsByPrice(100));
//		 System.out.println(facade.getAllPurchasedCouponsByType(CouponType.CLOTHING));
//		 facade.purchaseCoupon(facade.getCoupon(4));
        
		
        //Company facade				 

		
/*		CompanyFacade facade = (CompanyFacade) newsys.login("Next", "123456",ClientType.COMPANY);
		 
    	 Coupon coupon1 = new Coupon();
		 coupon1.setTitle("Sale949");
		 coupon1.setStartDate(java.sql.Date.valueOf("2017-07-12"));
		 coupon1.setEndDate(java.sql.Date.valueOf("2018-03-04"));
		 coupon1.setAmount(10); coupon1.setType(CouponType.CLOTHING);
		 coupon1.setPrice(90);
		 facade.createCoupon(coupon1);*/
		 
	}
	}
