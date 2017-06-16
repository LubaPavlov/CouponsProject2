package imports.d20170427coupProjDafnaWeiss.test;

import java.text.SimpleDateFormat;
import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.facade.ClientType;
import imports.d20170427coupProjDafnaWeiss.facade.CompanyFacade;
import imports.d20170427coupProjDafnaWeiss.facade.CouponSystemSingleton;
import imports.d20170427coupProjDafnaWeiss.facade.CustomerFacade;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
import imports.d20170427coupProjDafnaWeiss.util.StringToDate;

public abstract class TestFacade {

	// Get an Instance of the System Singleton
	private static CouponSystemSingleton system = CouponSystemSingleton.getInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static CustomerFacade customerFacade = new CustomerFacade();
	
	public static void main(String[] args){

		testAdminFacade();
		testCompanyFacade();
		testCustomerFacade();
		
		// Shut Down the Coupon System - CLOSE ALL CONNECTIONS and STOP THE DAILY TASK
		try	{
			system.shutdown();
		}
		catch (MyException e){
			System.err.println("ERROR trying the SHUT DOWN the coupon system from the TEST FACADE: " + e.getMessage());
		}
	}

	//------------------
	// TEST ADMIN FACADE
	//------------------
	
	private static void testAdminFacade()
	{
		System.out.println("*************************");
		System.out.println("*      A D M I N        *");
		System.out.println("*************************");
		
		AdminFacade adminFacade;
		Company company;
		Customer customer;
		
		try	{
			
			// Testing the LOGIN 
			String adminName = "admin";
			String adminPassword = "5678";				// the password is INCORRECT
			ClientType clientType = ClientType.ADMIN;
			
			adminFacade = (AdminFacade)system.login(adminName,adminPassword,clientType);
			
			if (adminFacade != null){
				System.out.println("LOGIN to Coupon System as ADMIN - SUCCEED!");
			}
			
			else {
				System.out.println("THE ADMIN name or password are INCORRECT - please try again");
				
				adminName = "admin";
				adminPassword = "1234";					// this password is OK
				adminFacade = (AdminFacade)system.login(adminName,adminPassword,clientType);
				 
				if (adminFacade != null){
					System.out.println("LOGIN to Coupon System as ADMIN - SUCCEED!");
				}
				else {
					System.out.println("TEST FAILED IN LOGIN ADMIN");
					return;
				}
			}
			
			//----------------------------------------
			// Testing COMPANY Methods in ADMIN FACADE
			//----------------------------------------
			System.out.println("");
			System.out.println("		******************************");
			System.out.println("		* A D M I N :  C O M P A N Y *");
			System.out.println("		******************************");
			
			// Testing CREATE COMPANY - return TRUE if succeed to CREATE a COMPANY		
			company = new Company("BBB","abcd","bbb@gmail.com");		// This Company Name is already exist
			
			if (adminFacade.createCompany(company)){
				System.out.println("SUCCEED TO ADD A NEW COMPANY: " + company.toString());	
			}
			
			else {
				System.out.println("FAILED ADD A COMPANY - please change the COMPANY NAME");	
				
				company = new Company("Osem","abcd","osem@gmail.com");		// This Company name is OK
				
				if (adminFacade.createCompany(company)){
					System.out.println("SUCCEED TO ADD A NEW COMPANY: " + company.toString());	
				}
				else {
					System.out.println("TEST FAILED IN CREATE COMPANY");
					return;
				}
			}
			
			// Testing DELETE COMPANY
			// delete the new Company that we created before
			adminFacade.removeCompany(company);
			System.out.println("REMOVE A NEW COMPANY: " + company.toString());	
			
			
			// deleting a given Company from the DB table
			company = adminFacade.getCompany(2);			// there is no COMPANY with this ID
			
			if (company != null) {
				adminFacade.removeCompany(company);
				System.out.println("REMOVE A DB COMPANY: " + company.toString()); 
			}
			
			else {
				System.out.println("REMOVE COMPANY: The COMPANY ID is INCORRECT");
				
				company = adminFacade.getCompany(35);		// This COMPANY is OK				
				adminFacade.removeCompany(company);
				System.out.println("REMOVE A DB COMPANY: " + company.toString()); 
			}
						
			// Testing UPDATE COMPANY
			company = adminFacade.getCompany(2);			// there is no COMPANY with this ID
			
			if ( company != null) {
				company.setPassword("update2");
				company.setEmail("update@gmail.com");
				
				adminFacade.updateCompany(company);
				System.out.println("UPDATE A COMPANY: " + company.toString());
			}
			
			else {
				System.out.println("UPDATE COMPANY: The COMPANY ID is INCORRECT");
				
				company = adminFacade.getCompany(1);		// This COMPANY is OK	
				if ( company != null) {
					company.setPassword("update1");
					company.setEmail("update@gmail.com");
					
					adminFacade.updateCompany(company);
					System.out.println("UPDATE A COMPANY: " + company.toString());
				}
				else {
					System.out.println("TEST FAILED trying to SHOW A COMPANY BY ID");
					return;
				}
			}
			
			// SHOW ALL COMPANIES
			System.out.println("");
			System.out.println("----------------------");
			System.out.println("ALL THE COMPANIES ARE:");
			System.out.println("----------------------");
			
			for (Company companyList: adminFacade.getAllCompanies()){
				System.out.println(companyList);
			}
			
			// SHOW COMPANY by ID
			company = adminFacade.getCompany(2);				// there is no COMPANY with this ID
			
			if ( company != null) {
				System.out.println("COMPANY BY ID: " + company);
			}
			
			else {
				System.out.println("The COMPANY ID is INCORRECT");
				
				company = adminFacade.getCompany(1);			// This COMPANY is OK	
				if ( company != null) {
					System.out.println("COMPANY BY ID: " + company);
				}
				else {
					System.out.println("TEST FAILED trying to SHOW A COMPANY BY ID");
					return;
				}
			}
			
			//-----------------------------------------
			// Testing CUSTOMER Methods in ADMIN FACADE
			//-----------------------------------------
			System.out.println("");
			System.out.println("		********************************");
			System.out.println("		* A D M I N :  C U S T O M E R *");
			System.out.println("		********************************");
			
			// Testing CREATE CUSTOMER - return TRUE if succeed to CREATE a CUSTOMER		
			customer = new Customer("Dafna","1234");					// This Customer Name is already exist
			
			if (adminFacade.createCustomer(customer)){
				System.out.println("SUCCEED TO ADD A NEW CUSTOMER: " + customer.toString());	
			}
			
			else {
				System.out.println("FAILED ADD A CUSTOMER - please change the CUSTOMER NAME");	
				
				customer = new Customer("Sarit","efgh");				// This Customer Name is OK
				
				if (adminFacade.createCustomer(customer)){
					System.out.println("SUCCEED TO ADD A NEW CUSTOMER: " + customer.toString());	
				}
				else {
					System.out.println("TEST FAILED IN CREATE CUSTOMER");
					return;
				}
			}
			
			// Testing DELETE CUSTOMER
			
			// delete the new Customer that we created before
			adminFacade.removeCustomer(customer);
			System.out.println("REMOVE A NEW CUSTOMER: " + customer.toString());	
			
			// deleting a given Customer from the DB table
			customer = adminFacade.getCustomer(6);				// there is no CUSTOMER with this ID
			
			if (customer != null) {
				adminFacade.removeCustomer(customer);
				System.out.println("REMOVE A DB CUSTOMER: " + customer.toString()); 
			}
			
			else {
				System.out.println("REMOVE CUSTOMER: The CUSTOMER ID is INCORRECT");
				
				customer = adminFacade.getCustomer(12);			// This CUSTOMER is OK				
				adminFacade.removeCustomer(customer);
				System.out.println("REMOVE A DB CUSTOMER: " + customer.toString()); 
			}
			
			// Testing UPDATE CUSTOMER
			customer = adminFacade.getCustomer(2);				// there is no COMPANY with this ID
			
			if (customer != null) {
				customer.setPassword("update2");
				
				adminFacade.updateCustomer(customer);
				System.out.println("UPDATE A CUSTOMER: " + customer.toString());
			}
			
			else {
				System.out.println("The CUSTOMER ID is INCORRECT");
				
				company = adminFacade.getCompany(1);			// This CUSTOMER is OK
				if ( company != null) {
					company.setPassword("update1");
					company.setEmail("update@gmail.com");
					
					adminFacade.updateCompany(company);
					System.out.println("UPDATE A COMPANY: " + company.toString());
				}
				else {
					System.out.println("TEST FAILED trying to SHOW A COMPANY BY ID");
					return;
				}
			}
			
			// SHOW ALL CUSTOMERS
			System.out.println("");
			System.out.println("----------------------");
			System.out.println("ALL THE CUSTOMERS ARE:");
			System.out.println("----------------------");
			
			for (Customer customerList: adminFacade.getAllCustomers()){
				System.out.println(customerList);
			}
			
			// SHOW CUSTOMER by ID
			customer = adminFacade.getCustomer(2);				// there is no COMPANY with this ID
			
			if (customer != null) {
				System.out.println("CUSTOMER BY ID: " + customer);
			}
			
			else {
				System.out.println("The CUSTOMER ID is INCORRECT");
				
				customer = adminFacade.getCustomer(1);			// This CUSTOMER is OK
				if (customer != null) {
					System.out.println("CUSTOMER BY ID: " + customer);
				}
				else {
					System.out.println("TEST FAILED trying to SHOW A CUSTOMER BY ID");
					return;
				}
			}
		}
		catch (MyException e){
			System.err.println("ERROR in TEST ADMIN Facade: " + e.getMessage());
		}
	}

	//--------------------
	// TEST COMPANY FACADE
	//--------------------
	private static void testCompanyFacade()	{
		System.out.println("");
		System.out.println("*************************");
		System.out.println("*     C O M P A N Y     *");
		System.out.println("*************************");
		
		CompanyFacade companyFacade = new CompanyFacade();
		Coupon coupon;
		
		try	{
			
			// Testing the LOGIN
			String name = "BBB";
			String password = "5678";						// The Password is INCORRECT							
			ClientType clientType = ClientType.COMPANY;
			
			Company company = (Company) system.login(name,password,clientType);	// Convert company from Loginable to Company
			
			if (company != null){
				System.out.println("LOGIN to Coupon System as COMPANY - SUCCEED!");
			}
			
			else {
				 name = "BBB";
				 password = "1234";							// This Password is OK
				 company = (Company) system.login(name,password,clientType);
				 
				 if (company != null){
						System.out.println("LOGIN to Coupon System as COMPANY - SUCCEED!");
				 }
				 else {
					 System.out.println("TEST FAILED IN LOGIN COMPANY");
					 return;
				 }
			}
			
			// Testing CREATE COUPON - return TRUE if succeed to CREATE a COUPON		
			coupon = new Coupon ("Free Meal",StringToDate.dateFormat("2017-01-01"),
					StringToDate.dateFormat("2018-01-01"),10000,CouponType.RESTURANTS,"Free business meal for a soldier",50,"");
			
			if (companyFacade.createCoupon(company,coupon)){
				System.out.println("SUCCEED TO ADD A NEW COUPON: " + coupon.getTitle() + ", ID = " + coupon.getId() + " to the COMPANY " + company.getCompName());	
			}
			
			else {
				System.out.println("FAILED ADD A COUPON - please change the COUPON TITLE");	
				
				coupon = new Coupon ("Free Meal for Soldiers",StringToDate.dateFormat("2017-01-01"),
						StringToDate.dateFormat("2018-01-01"),10000,CouponType.RESTURANTS,"Free business meal for a soldiers",50,"");
				
				if (companyFacade.createCoupon(company,coupon)){
					System.out.println("SUCCEED TO ADD A NEW COUPON: " + coupon.getTitle() + ", ID = " + coupon.getId() + " to the COMPANY " + company.getCompName());	
				}
				else {
					System.out.println("TEST FAILED IN CREATE COUPON");
					return;
				}
			}
			
			// Testing DELETE COUPON
			
			// delete the new Coupon that we created before
			companyFacade.removeCoupon(company,coupon);
			System.out.println("REMOVE A NEW COUPON: " + coupon.toString());
				
			// deleting a given Coupon from the DB table
			coupon = companyFacade.getCoupon(6);				// there is no COUPON with this ID
			
			if (coupon != null) {
				companyFacade.removeCoupon(company,coupon);
				System.out.println("REMOVE A DB COUPON: " + coupon.toString()); 
			}
			
			else {
				System.out.println("REMOVE COUPON: The COUPON ID is INCORRECT");
				
				coupon = companyFacade.getCoupon(21);			// this COUPON is OK				
				companyFacade.removeCoupon(company,coupon);
				System.out.println("REMOVE A DB COUPON: " + coupon.toString()); 
			}
			
			// Testing UPDATE COUPON (END DATE and PRICE)
			coupon = companyFacade.getCoupon(6);			// there is no COUPON with this ID
			
			if (coupon != null) {
				coupon.setEndDate(StringToDate.dateFormat("2018-01-01"));
				coupon.setPrice(200);
				
				companyFacade.updateCoupon(company,coupon);
				System.out.println("UPDATE A COUPON: End Date = " + sdf.format(coupon.getEndDate()) + " Price = " + coupon.getPrice());
			}
			
			else {
				System.out.println("UPDATE COUPON: The COUPON ID is INCORRECT");
				
				coupon = companyFacade.getCoupon(4);		// this COUPON is OK
				if (coupon != null) {
					coupon.setEndDate(StringToDate.dateFormat("2018-01-01"));
					coupon.setPrice(100);
					
					companyFacade.updateCoupon(company,coupon);
					System.out.println("UPDATE A COUPON: End Date = " + sdf.format(coupon.getEndDate()) + " Price = " + coupon.getPrice());
				}
				else {
					System.out.println("TEST FAILED trying to SHOW A COMPANY BY ID");
					return;
				}
			}
			
			Collection<Coupon> companyCoupons = companyFacade.getCoupons(company);
			
			// SHOW all the COMPANY's Fields
			System.out.println(companyFacade.showCompanyFields(company));
			
			// SHOW ALL COMPANY'S COUPONS
			// checking if this Company has any COUPONS
			if (!companyCoupons.isEmpty()){
				System.out.println("");
				System.out.println("----------------------------------------------------");
				System.out.println("FOR COMPANY " + company.getCompName() + " ALL THE COUPONS ARE:");
				System.out.println("----------------------------------------------------");
				
				for (Coupon companyCoupon : companyCoupons){
					System.out.println(companyCoupon);
				}
				
				// SHOW ALL COMPANY'S COUPONS BY TYPE
				CouponType couponType = CouponType.RESTURANTS;
				Collection<Coupon> compCouponsByType = companyFacade.getCouponsByType(company, couponType);
				
				// checking if this Company has any COUPONS of the given TYPE
				if (!compCouponsByType.isEmpty()){
					System.out.println("");
					System.out.println("-------------------------------------------------------------");
					System.out.println("FOR COMPANY " + company.getCompName() + " ALL THE COUPONS of TYPE " + couponType + " are:");
					System.out.println("-------------------------------------------------------------");
					
					for (Coupon compCouponByType : compCouponsByType){
						System.out.println(compCouponByType);
					}
				}
				else {
					System.out.println("The COMPANY " + company.getCompName() + " has NO COUPONS of TYPE " + couponType);
				}
				
				// SHOW ALL COMPANY'S COUPONS BY MAX PRICE
				double maxPrice = 50;
				Collection<Coupon> compCouponsByPrice = companyFacade.getCouponsByPrice(company, maxPrice);
				
				// checking if this Company has any COUPONS with Price less then the given Maximum Price
				if (!compCouponsByPrice.isEmpty()){
					System.out.println("");
					System.out.println("-------------------------------------------------------------");
					System.out.println("FOR COMPANY " + company.getCompName() + " ALL THE COUPONS less then " + maxPrice + " Shekels are:");
					System.out.println("-------------------------------------------------------------");
					
					for (Coupon compCouponByPrice : compCouponsByPrice){
						System.out.println(compCouponByPrice);
					}
				}
				else {
					System.out.println("The COMPANY " + company.getCompName() + " has NO COUPONS less then " + maxPrice + " Shekels");
				}
				
				// SHOW ALL COMPANY'S COUPONS THAT EXPIRE BEFORE THE MAX DATE
				String maxDate = "2018-01-01";
				Collection<Coupon> compCouponsByDate = companyFacade.getCouponsByDate(company, maxDate);
				
				// checking if this Company has any COUPONS that expire before the given Maximum Date
				if (!compCouponsByDate.isEmpty()){
					System.out.println("");
					System.out.println("------------------------------------------------------------------");
					System.out.println("FOR COMPANY " + company.getCompName() + " ALL THE COUPONS that expire before " + maxDate + " are:");
					System.out.println("------------------------------------------------------------------");
					
					for (Coupon compCouponByDate : compCouponsByDate){
						System.out.println(compCouponByDate);
					}
				}
				else {
					System.out.println("The COMPANY " + company.getCompName() + " has NO COUPONS that expire before " + maxDate);
				}
			}
			
			else {
				System.out.println("THE COMPANY " + company.getCompName() + " HAS NO COUPONS YET");
			}
		}
		catch (MyException e){
			System.err.println("ERROR in TEST COMPANY Facade: " + e.getMessage());
		}
	}
	
	//---------------------
	// TEST CUSTOMER FACADE
	//---------------------
	private static void testCustomerFacade(){
		System.out.println("");
		System.out.println("*************************");
		System.out.println("*     C U S T O M E R   *");
		System.out.println("*************************");
		
		Coupon coupon;
		
		try	{
			
			// Testing the LOGIN
			String name = "Dafna";
			String password = "12345";						// the password is INCORRECT
			ClientType clientType = ClientType.CUSTOMER;
			
			Customer customer = (Customer) system.login(name,password,clientType);	// Convert customer from Loginable to Customer
			
			if (customer != null){
				System.out.println("LOGIN to Coupon System as CUSTOMER - SUCCEED!");
			}
			else {
				 name = "Dafna";
				 password = "abcde";						// this password is OK
				 customer = (Customer) system.login(name,password,clientType);
				 
				 if (customer != null){
						System.out.println("LOGIN to Coupon System as CUSTOMER - SUCCEED!");
				 }
				 else {
					 System.out.println("TEST FAILED IN LOGIN CUSTOMER");
					 return;
				 }
			}
			
			// Testing PURCHASE COUPON by Customer
			coupon = customerFacade.getCoupon(6);	// there is no COUPON with this ID
			
			if (coupon == null) {
				System.out.println("PURCHASE COUPON: The COUPON ID is INCORRECT!");
				coupon = customerFacade.getCoupon(10);	// This COUPON is already exist in the Customer COUPONS LIST
				
				if (!checkCoupon(customer,coupon)){
					
					coupon = customerFacade.getCoupon(3);	// this COUPON is already been expired
					if (!checkCoupon(customer,coupon)){
						
						coupon = customerFacade.getCoupon(4);	// this COUPON is OUT OF STOCK
						if (!checkCoupon(customer,coupon)){
							
							coupon = customerFacade.getCoupon(11);	// this COUPON is OK
							if (!checkCoupon(customer,coupon)){
								System.out.println("TEST FAILED trying to PURCHASE A COUPON BY a CUSTOMER");
								return;
							}
						}
					}
				}
			}
			
			// SHOW ALL CUSTOMER'S COUPONS
			Collection<Coupon> customerCoupons = customerFacade.getAllPurchasedCoupons(customer);
			// checking if this Customer has any COUPONS
			if (!customerCoupons.isEmpty()){
				System.out.println("");
				System.out.println("-----------------------------------------------------");
				System.out.println("FOR CUSTOMER " + customer.getCustName() + " ALL THE COUPONS ARE:");
				System.out.println("-----------------------------------------------------");
				
				for (Coupon customerCoupon : customerCoupons){
					System.out.println(customerCoupon);
				}
				
				// SHOW ALL CUSTOMER'S COUPONS BY TYPE
				CouponType couponType = CouponType.RESTURANTS;
				Collection<Coupon> custCouponsByType = customerFacade.getAllPurchasedCouponsByType(customer, couponType);
				
				// checking if this Customer has any COUPONS of the given TYPE
				if (!custCouponsByType.isEmpty()){
					System.out.println("");
					System.out.println("-------------------------------------------------------------");
					System.out.println("FOR CUSTOMER " + customer.getCustName() + " ALL THE COUPONS of TYPE " + couponType + " are:");
					System.out.println("-------------------------------------------------------------");
					
					for (Coupon custCouponByType : custCouponsByType){
						System.out.println(custCouponByType);
					}
				}
				else {
					System.out.println("The CUSTOMER " + customer.getCustName() + " has NO COUPONS of TYPE " + couponType);
				}
				
				// SHOW ALL CUSTOMER'S COUPONS BY MAX PRICE
				double maxPrice = 50;
				Collection<Coupon> custCouponsByPrice = customerFacade.getAllPurchasedCouponsByPrice(customer, maxPrice);
				
				// checking if this Company has any COUPONS with Price less then the given Maximum Price
				if (!custCouponsByPrice.isEmpty()){
					System.out.println("");
					System.out.println("-------------------------------------------------------------");
					System.out.println("FOR CUSTOMER " + customer.getCustName() + " ALL THE COUPONS less then " + maxPrice + " Shekels are:");
					System.out.println("-------------------------------------------------------------");
					
					for (Coupon custCouponByPrice : custCouponsByPrice){
						System.out.println(custCouponByPrice);
					}
				}
				else {
					System.out.println("The CUSTOMER " + customer.getCustName() + " has NO COUPONS less then " + maxPrice + " Shekels");
				}
			}
			
			else {
				System.out.println("THE CUSTOMER " + customer.getCustName() + " HAS NO COUPONS YET");
			}
		}
		catch (MyException e){
			System.err.println("ERROR in TEST CUSTOMER Facade: " + e.getMessage());
		}
	}
	
	// Checking if the given Customer can PURCHASE the given COUPON
	private static boolean checkCoupon(Customer customer, Coupon coupon){
		try	{
			if(customerFacade.purchaseCoupon(customer, coupon)){
				System.out.println ("SUCCEED TO PURCHASE A COUPON");
				System.out.println ("CUSTOMER " + customer.getCustName()+ " PURCHASED A COUPON: " + coupon.getTitle() 
				+ ", ID = " + coupon.getId() + ", AMOUNT = " + coupon.getAmount());
				return true;
			}
			else {
				System.out.println("The Coupon is INCORRECT - try to buy another one");
				return false;
			}
		}
		catch (MyException e){
			System.err.println("ERROR in PURCHASE COUPON by a CUSTOMER: " + e.getMessage());
			return false;
		}
	}

}
