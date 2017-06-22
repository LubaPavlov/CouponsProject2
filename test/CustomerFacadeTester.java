package imports.d20170427coupProjDafnaWeiss.test;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.ClientType;
import imports.d20170427coupProjDafnaWeiss.facade.CustomerFacade;
import imports.d20170427coupProjDafnaWeiss.model.Coupon;
import imports.d20170427coupProjDafnaWeiss.model.CouponType;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
import imports.d20170427coupProjDafnaWeiss.util.StringToDate;
/**
 * Test all the CUSTOMER Facade methods
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CustomerFacadeTester {
	
	private static CustomerFacade customerFacade = null;
	private static Customer customer = null;
	/**
	 * Test all the CUSTOMER Facade methods
	 * @throws MyException SQL exception 
	 */
	public static void testCustomerFacade() throws MyException	{
		System.out.println("");
		System.out.println("*************************");
		System.out.println("*     C U S T O M E R   *");
		System.out.println("*************************");

		ClientType clientType = ClientType.CUSTOMER;
		
		// Testing the LOGIN and getting the Logged In Customer
		System.out.println("");
		System.out.println("-------------CUSTOMER LOGIN-------------");
		
		String name = "Dafna";
		String password = "12345";															// The Password is INCORRECT							
		
		customerFacade = (CustomerFacade)FacadeTester.system.login(name,password,clientType);
		
		if (customerFacade != null){
			
			customer = customerFacade.getLoginCustomer();
			System.out.println("TestCustomerFacade: SUCCEED LOGIN to Coupon System with CUSTOMER: " + name);
			// after succeeding in LOGIN - Testing the Coupon's Methods in the Customer Facade
			testCouponMethods();
		}
		
		else {
			// trying another Password
			name = "Dafna";
			password = "abcde";																// This Password is OK
			customerFacade = (CustomerFacade)FacadeTester.system.login(name,password,clientType);
			 
			 if (customerFacade != null){
				 
				 customer = customerFacade.getLoginCustomer();
				 System.out.println("TestCompanyFacade: SUCCEED LOGIN to Coupon System with COMPANY: " + name);
				// after succeeding in LOGIN - Testing the Coupon's Methods in the Company Facade
				 testCouponMethods();
			 }
			 
			 else {
				 System.out.println("TestCustomerFacade: TEST FAILED IN LOGIN CUSTOMER: " + name);
				 return;
			 }
		}
	}
	
	private static void testCouponMethods() throws MyException	{
		//----------------------------------------------------------------------
		// Testing COUPON's Methods in CUSTOMER FACADE of the Logged in Customer
		//----------------------------------------------------------------------
		System.out.println("-----------------------------------------");
		System.out.println("");
		System.out.println("		* C U S T O M E R: " + customer.getCustName() + " *");
		System.out.println("");
		System.out.println("		**********************");
		System.out.println("		*     C O U P O N    *");
		System.out.println("		**********************");
		
		// Testing PURCHASE COUPON by the logged in Customer
		//--------------------------------------------------
		System.out.println("");
		System.out.println("--------------PURCHASE COUPON-------------");
		
		Coupon coupon = customerFacade.getCoupon(6);									// there is no COUPON with this ID
		
		if (coupon != null) {
			
			if (customerFacade.purchaseCoupon(customer,coupon)){
				System.out.println("TestCustomerFacade: SUCCEED TO PURCHASE A NEW COUPON: id = " + coupon.getId());
			}
			
			else {
				System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: id = " + coupon.getId());
			}
		}
			
		else {
			System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: The COUPON ID is INCORRECT!");
				
			// try to purchase another coupon
			coupon = customerFacade.getCoupon(13);										// the Customer had already purchased this COUPON before
			
			if (coupon != null) {
				
				if (customerFacade.purchaseCoupon(customer,coupon)){
					System.out.println("TestCustomerFacade: SUCCEED TO PURCHASE A NEW COUPON: id = " + coupon.getId());
				}
				
				else {
					System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: id = " + coupon.getId());
					
					// try to purchase another coupon
					// create new coupon with END DATE that is already been expired
					CouponDBDAO couponDBDAO = new CouponDBDAO();
					coupon = new Coupon ("Test Daily Task",StringToDate.dateFormat("2017-01-01"),StringToDate.dateFormat("2017-02-01"),
																			200,CouponType.RESTURANTS,"test for the Daily Task",150,"");
					couponDBDAO.createCoupon(coupon);
					Long id = coupon.getId();
					coupon = customerFacade.getCoupon(id);								// this COUPON is already been EXPIRED
					
					if (coupon != null) {
						
						if (customerFacade.purchaseCoupon(customer,coupon)){
							System.out.println("TestCustomerFacade: SUCCEED TO PURCHASE A NEW COUPON: id = " + coupon.getId());
						}
						
						else {
							System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: id = " + coupon.getId());
							
							// try to purchase another coupon
							coupon = customerFacade.getCoupon(13);						// this COUPON is OUT OF STOCK
							
							if (coupon != null) {
								
								if (customerFacade.purchaseCoupon(customer,coupon)){
									System.out.println("TestCustomerFacade: SUCCEED TO PURCHASE A NEW COUPON: id = " + coupon.getId());
								}
								
								else {
									System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: id = " + coupon.getId());
									
									// try to purchase another coupon
									coupon = customerFacade.getCoupon(1);				// this COUPON is OK
									
									if (coupon != null) {
										
										if (customerFacade.purchaseCoupon(customer,coupon)){
											System.out.println("TestCustomerFacade: SUCCEED TO PURCHASE A NEW COUPON: id = " + coupon.getId());
										}
										
										else {
											System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: id = " + coupon.getId());
										}
									}
									
									else {
										System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: The COUPON ID is INCORRECT!");
									}
								}
							}
							
							else {
								System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: The COUPON ID is INCORRECT!");
							}
						}
					}
					
					else {
						System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: The COUPON ID is INCORRECT!");
					}
				}
			}
			
			else {
				System.out.println("TestCustomerFacade:FAILED TO PURCHASE COUPON: The COUPON ID is INCORRECT!");
			}
		}
		System.out.println("-----------------------------------------");
		
		// SHOW ALL PARCHASED COUPONS of the logged in Company
		//----------------------------------------------------
		// checking if the logged in CUSTOMER had purchased any COUPONS 
		Collection<Coupon> customerCoupons = customerFacade.getAllPurchasedCoupons(customer);			// getting all the COUPONS of the Customer	
		
		if (!customerCoupons.isEmpty()){
				
			// SHOW ALL COMPANY'S COUPONS 
			System.out.println("");
			System.out.println("-----------------------------------------");
			System.out.println("FOR CUSTOMER " + customer.getCustName() + " ALL THE COUPONS ARE:");
			System.out.println("-----------------------------------------");
			
			for (Coupon couponList: customerCoupons){
				System.out.println(couponList);
			}
			System.out.println("-----------------------------------------");
		
			// SHOW ALL PARCHASED COUPONS of the logged in Customer BY TYPE
			CouponType couponType = CouponType.RESTURANTS;
			Collection<Coupon> custCouponsByType = customerFacade.getAllPurchasedCouponsByType(customer, couponType);
			
			// checking if the logged in Customer has purchased any COUPONS with the given TYPE
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
			System.out.println("-----------------------------------------");
			
			// SHOW ALL PARCHASED COUPONS of the logged in Customer BY MAXIMUM PRICE
			double maxPrice = 50;
			Collection<Coupon> custCouponsByPrice = customerFacade.getAllPurchasedCouponsByPrice(customer, maxPrice);
			
			// checking if there is any COUPONS less then the given Maximum Price that belong to the logged in Company
			if (!custCouponsByPrice.isEmpty()){
				System.out.println("");
				System.out.println("-------------------------------------------------------------");
				System.out.println("FOR CUSTOMER " + customer.getCustName() + " ALL THE COUPONS less or equal to " + maxPrice + " Shekels are:");
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
			System.out.println("THE CUSTOMER " + customer.getCustName() + " DOESN'T PURCHASED ANY COUPONS!");
		}
		System.out.println("");
		System.out.println("-----------END CUSTOMER FACADE-----------");
	}
}
		
