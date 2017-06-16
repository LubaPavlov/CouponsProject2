package imports.d20170427coupProjDafnaWeiss.test;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.facade.ClientType;
import imports.d20170427coupProjDafnaWeiss.facade.CompanyFacade;
import imports.d20170427coupProjDafnaWeiss.facade.CustomerFacade;
import imports.d20170427coupProjDafnaWeiss.model.Company;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.util.StringToDate;
/**
 * Test all the COMPANY Facade methods
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CompanyFacadeTester {
	
	private static CompanyFacade companyFacade = null;
	private static Company company = null;
	/**
	 * Test all the COMPANY Facade methods
	 * @throws MyException SQL exception 
	 */
	public static void testCompanyFacade() throws MyException {
		System.out.println("");
		System.out.println("*************************");
		System.out.println("*     C O M P A N Y     *");
		System.out.println("*************************");
		
		ClientType clientType = ClientType.COMPANY;
		
		// Testing the LOGIN and getting the Logged In Company
		System.out.println("");
		System.out.println("---------------COMPANY LOGIN-------------");
		
		String name = "BBB";
		String password = "5678";															// The Password is INCORRECT							
		companyFacade = (CompanyFacade)FacadeTester.system.login(name,password,clientType);
		
		if (companyFacade != null){
			
			company = companyFacade.getLoginCompany();
			System.out.println("TestCompanyFacade: SUCCEED LOGIN to Coupon System with COMPANY: " + name);
			// after succeeding in LOGIN - Testing the Coupon's Methods in the Company Facade
			testCouponMethods();
		}
		
		else {
			// trying another Password
			 name = "BBB";
			 password = "1234";																// This Password is OK
			 companyFacade = (CompanyFacade)FacadeTester.system.login(name,password,clientType);
			 
			 if (companyFacade != null){
				 
				 company = companyFacade.getLoginCompany();
				 System.out.println("TestCompanyFacade: SUCCEED LOGIN to Coupon System with COMPANY: " + name);
				// after succeeding in LOGIN - Testing the Coupon's Methods in the Company Facade
				 testCouponMethods();
			 }
			 
			 else {
				 System.out.println("TestCompanyFacade: TEST FAILED IN LOGIN COMPANY: " + name);
				 return;
			 }
		}
	}
	
	private static void testCouponMethods() throws MyException	{
		//--------------------------------------------------------------------
		// Testing COUPON's Methods in COMPANY FACADE of the Logged in Company
		//--------------------------------------------------------------------
		System.out.println("-----------------------------------------");
		System.out.println("");
		System.out.println("		* C O M P A N Y: " + company.getCompName() + " *");
		System.out.println("");
		System.out.println("		**********************");
		System.out.println("		*     C O U P O N    *");
		System.out.println("		**********************");
		
		// Testing CREATE COUPON - return TRUE if succeed to CREATE a COUPON
		System.out.println("");
		System.out.println("---------------CREATE COUPON-------------");
		
		Coupon coupon = new Coupon("Free Desert",StringToDate.dateFormat("2017-01-01"),StringToDate.dateFormat("2018-01-01"),1000,
						CouponType.RESTURANTS,"Free desert on birthday",10,"");				// This Coupon Title is already exist
		long id = 0;
			
		if (companyFacade.createCoupon(companyFacade.getLoginCompany(),coupon)){
			System.out.println("TestCompanyFacade: SUCCEED TO ADD A NEW COUPON: " + coupon.toString());	
		}
		
		else {
			System.out.println("TestCompanyFacade: FAILED ADD A COUPON: " + coupon.getTitle() + " - please change the COUPON TITLE");
				
			// trying another coupon
			// This Coupon Title is OK - this will be the one to be deleted from the DB
			coupon = new Coupon ("Free Meal",StringToDate.dateFormat("2017-01-01"),StringToDate.dateFormat("2017-04-01"),
																	3000,CouponType.RESTURANTS,"Free business meal for 1 person",60,"");	
			
			if (companyFacade.createCoupon(company,coupon)){
				// add this coupon to the customer_coupon join table in DB
				CustomerFacade customerFacade = new CustomerFacade();
				AdminFacade adminFacade = new AdminFacade();
				
				customerFacade.purchaseCoupon(adminFacade.getCustomer(2), coupon);
				customerFacade.purchaseCoupon(adminFacade.getCustomer(3), coupon);
				
				id = coupon.getId();
				System.out.println("TestCompanyFacade: SUCCEED TO ADD A NEW COUPON: " + coupon.toString());	
			}
			
			else {
				System.out.println("TestCompanyFacade: FAILED ADD A COUPON: " + coupon.getTitle() + " - second time");
				return;
			}
			
			// This Coupon will be the one to be deleted as "new coupon"
			coupon = new Coupon ("Free Meal for Soldiers",StringToDate.dateFormat("2017-01-01"),StringToDate.dateFormat("2018-01-01"),
					10000,CouponType.RESTURANTS,"Free business meal for a soldiers",50,"");	
			
			if (companyFacade.createCoupon(company,coupon)){
				System.out.println("TestCompanyFacade: SUCCEED TO ADD A NEW COUPON: " + coupon.toString());	
			}
			
			else {
				System.out.println("TestCompanyFacade: FAILED ADD A COUPON: " + coupon.getTitle() + " - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
			
		// Testing REMOVE COUPON
		//-----------------------
		// delete the new Coupon that we created before
		System.out.println("");
		System.out.println("---------------REMOVE COUPON-------------");
		
		companyFacade.removeCoupon(company, coupon);
		System.out.println("TestCompanyFacade: SUCCEED TO REMOVE A NEW COUPON: id = " + coupon.getId());	
		
		// deleting a given Coupon from the DB table
		coupon = companyFacade.getCoupon(6);												// there is no COUPON with this ID
		
		if (coupon != null) {
			
			// checking if this Coupon belongs to The logged in Company
			if(companyFacade.removeCoupon(company, coupon)){											
				System.out.println("TestCompanyFacade: SUCCEED TO REMOVE A NEW COUPON: id = " + coupon.getId());	
			}
			
			else {
				System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO REMOVE THIS COUPON, id = " + coupon.getId());	
			}
		}
				
		else {
			System.out.println("TestCompanyFacade: FAILED REMOVE COUPON: The COUPON ID is INCORRECT");
			
			// trying another coupon
			coupon = companyFacade.getCoupon(3);											// this Coupon isn't belong to the Company
			
			if (coupon != null) {
				
				// checking if this Coupon belongs to The logged in Company
				if(companyFacade.removeCoupon(company, coupon)){											
					System.out.println("TestCompanyFacade: SUCCEED TO REMOVE A NEW COUPON: id = " + coupon.getId());	
				}
				
				else {
					System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO REMOVE THIS COUPON, id = " + coupon.getId());
					
					// trying another coupon
					coupon = companyFacade.getCoupon(id);											// this Coupon is OK
					
					if (coupon != null) {
						
						// checking if this Coupon belongs to The logged in Company
						if(companyFacade.removeCoupon(company, coupon)){											
							System.out.println("TestCompanyFacade: SUCCEED TO REMOVE A NEW COUPON: id = " + id);	
						}
						
						else {
							System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO REMOVE THIS COUPON, id = " + id);	
						}
					}
					else {
						System.out.println("TestCompanyFacade: FAILED REMOVE COUPON: The COUPON ID is INCORRECT");
						return;
					}
				}
			}
			else {
				System.out.println("TestCompanyFacade: FAILED REMOVE COUPON: The COUPON ID is INCORRECT");
			}
			System.out.println("-----------------------------------------");
			// Testing UPDATE COUPON (END DATE and PRICE)
			System.out.println("");
			System.out.println("---------------UPDATE COUPON-------------");
			
			coupon = companyFacade.getCoupon(6);												// there is no COUPON with this ID
				
			if (coupon != null) {
				
				coupon.setEndDate(StringToDate.dateFormat("2018-01-01"));
				coupon.setPrice(200);
				
				// checking if this Coupon belongs to The logged in Company
				if(companyFacade.updateCoupon(company, coupon)){											
					System.out.println("TestCompanyFacade: SUCCEED TO UPDATE A NEW COUPON: id = " + coupon.getId());	
				}
				
				else {
					System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO UPDATE THIS COUPON, id = " + coupon.getId());	
				}
			}
				
			else {
				System.out.println("TestCompanyFacade: FAILED TO UPDATE COUPON: The COUPON ID is INCORRECT");
				
				// trying another coupon
				coupon = companyFacade.getCoupon(3);											// this Coupon isn't belong to the Company
					
				if (coupon != null) {
					
					coupon.setEndDate(StringToDate.dateFormat("2018-01-01"));
					coupon.setPrice(50);
					
					// checking if this Coupon belongs to The logged in Company
					if(companyFacade.updateCoupon(company, coupon)){											
						System.out.println("TestCompanyFacade: SUCCEED TO UPDATE A NEW COUPON: id = " + coupon.getId());	
					}
					
					else {
						System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO UPDATE THIS COUPON, id = " + coupon.getId());
						
						// trying another coupon
						coupon = companyFacade.getCoupon(11);									// This COUPON is OK	
						
						if (company != null) {
							
							coupon.setEndDate(StringToDate.dateFormat("2017-06-01"));
							coupon.setPrice(100);
							
							// checking if this Coupon belongs to The logged in Company
							if(companyFacade.updateCoupon(company, coupon)){											
								System.out.println("TestCompanyFacade: SUCCEED TO UPDATE A NEW COUPON: id = " + coupon.getId());	
							}
							
							else {
								System.out.println("TestCompanyFacade: YOU DON'T ALLOWED TO UPDATE THIS COUPON, id = " + coupon.getId());	
							}
						}
						
						else {
							System.out.println("TestCompanyFacade: FAILED TO UPDATE COUPON: The COUPON ID is INCORRECT");
							return;
						}
					}
				}
					
				else {
					System.out.println("TestCompanyFacade: FAILED TO UPDATE COUPON: The COUPON ID is INCORRECT");
					
				}
				System.out.println("-----------------------------------------");
		
				// SHOW all the COMPANY's Fields 
				Collection<Coupon> companyCoupons = companyFacade.getCoupons(company);			// getting all the COUPONS of the Company	
				
				System.out.println("");
				System.out.println("-----------------------------------------");
				System.out.println("COMPANY: " + company.getCompName() + " ALL THE FILEDS ARE:");
				System.out.println("-----------------------------------------");
				System.out.println(companyFacade.showCompanyFields(company));
				System.out.println("-----------------------------------------");
				
				// SHOW ALL COUPONS of the logged in Company
				//------------------------------------------
				// checking if there is any COUPONS that belong to the logged in Company
				
				if (!companyCoupons.isEmpty()){
						
					// SHOW ALL COMPANY'S COUPONS 
					System.out.println("");
					System.out.println("-----------------------------------------");
					System.out.println("COMPANY " + company.getCompName() + " ALL THE COUPONS ARE:");
					System.out.println("-----------------------------------------");
					
					for (Coupon couponList: companyCoupons){
						System.out.println(couponList);
					}
					System.out.println("-----------------------------------------");
					
					// SHOW ALL COMPANY'S COUPONS BY TYPE
					CouponType couponType = CouponType.RESTURANTS;
					Collection<Coupon> compCouponsByType = companyFacade.getCouponsByType(company, couponType);
						
					// checking if there is any COUPONS with the given TYPE that belong to the logged in Company 
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
					System.out.println("-----------------------------------------");
					
					// SHOW ALL COMPANY'S COUPONS BY MAXIMUM PRICE
					double maxPrice = 50;
					Collection<Coupon> compCouponsByPrice = companyFacade.getCouponsByPrice(company, maxPrice);
					
					// checking if there is any COUPONS less then the given Maximum Price that belong to the logged in Company
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
						System.out.println("The COMPANY " + company.getCompName() + " has NO COUPONS less or equal to " + maxPrice + " Shekels");
					}
					System.out.println("-----------------------------------------");
						
					// SHOW ALL COMPANY'S COUPONS with END DATE less then the MAXIMUN DATE
					String maxDate = "2017-08-01";
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
					System.out.println("-----------------------------------------");
				}
					
				else {
					System.out.println("THE COMPANY: " + company.getCompName() + " HAS NO COUPONS!");
				}
				System.out.println("");
				System.out.println("------------END COMPANY FACADE-----------");
			}
		}
	}
}
	

