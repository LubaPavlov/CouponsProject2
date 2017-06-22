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
import com.project.facade.AdminFacade;
import com.project.facade.CouponClientFacade;
import com.project.facade.CustomerFacade;

public class MyTestJDBC {

	public static void main(String[] args) throws DAOException, LoginException {

		CouponSystem newsys = CouponSystem.getInstance();
		AdminFacade facade = (AdminFacade) newsys.login("admin", "1234", ClientType.ADMIN);
		
		//facade.createCompany(new Company("Golf", "123456","gold@gmail.com"));
		System.out.println(facade.getAllCompanies());
		Company comptorem = facade.getCompanyById(3);
		facade.removeComapny(comptorem);
		
		//CustomerFacade custFacade = (CustomerFacade) newsys.loginAsCustomer("Leo", "123123", ClientType.CUSTOMER);
		
		//Collection<Coupon> couponim = custFacade.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);
	    
		//System.out.println(couponim);
		
//		custFacade.PurchaseCoupon(new CouponDBDAO().getCoupon(2));
		
		// CouponClientFacade.login("admin", "1234", ClientType.ADMIN);
		// AdminFacade.createCompany(new Company ("Dell",
		// "12345","dell@gmail.com"));

		//
		// Scanner input = new Scanner(System.in);
		// System.out.println("Enter Username : ");
		// String name = input.next();
		// System.out.println("Enter Password : ");
		// String password = input.next();
		//
		// newsys2.login(name, password, ClientType.ADMIN);

		// newsys2.getCustomerDao();
		// AdminFacade adminFacade = new AdminFacade();
		// System.out.println(adminFacade.getAllCompanies());
		// CouponDAO couponDAO = new CouponDBDAO();
		// CustomerDAO customerDAO = new CustomerDBDAO();
		// CompanyDAO companyDAO = new CompanyDBDAO();

//		 CustomerDAO cDao = new CustomerDBDAO();
//		System.out.println( cDao.getCustomerId("Leo"));
//		 cDao.login("Leo", "123123");
		// Customer customer = new Customer("Leo","123123");
		// cDao.createCustomer(customer);
		// Customer customer2 = new Customer("Lilah","123456");
		// cDao.createCustomer(customer2);
		// Customer customer3 = new Customer("Idan","123456789");
		// cDao.createCustomer(customer3);

		// cDao.getCoupons(cDao.getCustomer(1));

		// CompanyDAO compDao = new CompanyDBDAO();
		// compDao.getCompany(3);
		// System.out.println(compDao.getCoupons(3));
		//
		// CouponDAO couponDao = new CouponDBDAO();
		// couponDao.getCouponByType(CouponType.FOOD);

		// System.out.println(cDao.getCustomer(1));
		// cDao.login("Fox","123456");
		// cDao.updateCustomer(customer3);
		// CompanyDAO compDao = new CompanyDBDAO();
		// Company company1 = new Company("Fox", "123456","fox@gmail.com");
		// compDao.createCompany(company1);
		// System.out.println(compDao.getAllCompanies());
		// compDao.login("Fox", "123456");
		// Company company = new Company("Fox", "123456","fox@gmail.com");
		// compDao.createCompany(company);

		// ==CouponDAO couponDao = new CouponDBDAO();
		// Coupon coupon1 = new Coupon();
		//// couponDao.createCoupon(couponnew);
		// couponnew.setTitle("dddddd");
		// couponDao.updateCoupon(couponnew);
		// couponDao.removeCoupon(couponDao.getCoupon(4));
		// Coupon coupon4 = new Coupon();
		// coupon4.setTitle("10% Off");
		// coupon4.setStartDate(java.sql.Date.valueOf("2018-02-04"));
		// coupon4.setEndDate(java.sql.Date.valueOf("2018-03-04"));
		// coupon4.setAmount(20);
		// coupon4.setType(CouponType.SPORTS);
		// coupon4.setPrice(33);
		// couponDao.createCoupon(coupon4);

	}
}
