package imports.d20170427coupProjDafnaWeiss.test;

import java.text.SimpleDateFormat;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.facade.CouponSystemSingleton;
/**
 * Test all the Facade Classes: The Main Class who runs the test
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class FacadeTester {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// Get an Instance of the System Singleton
	static CouponSystemSingleton system = CouponSystemSingleton.getInstance();
	/**
	 * The Main class who runs the TEST on all the Facade Classes
	 * @param args The main method  parameter
	 */
	public static void main(String[] args){
		
		try	{
			// TEST ADMIN FACADE
			AdminFacade adminFacade = AdminFacadeLoginTester.adminLogin();
			
			if(adminFacade != null){												// if Test Login Admin SUCCEED
				System.out.println("-----------------------------------------");
				
				AdminFacadeCompanyTester.testAdminFacadeCompany(adminFacade);		// Test Admin Company methods
				AdminFacadeCustomerTester.testAdminFacadeCustomer(adminFacade);		// Test Admin Customer methods
			}
			
			else {
				System.out.println("FacadeTester: FAILED TO LOGIN AS ADMIN");
			}
			// TEST COMPANY FACADE
			CompanyFacadeTester.testCompanyFacade();				
			
			// TEST CUSTOMER FACADE
			CustomerFacadeTester.testCustomerFacade();	
			
		}
		
		catch (MyException e){
			System.err.println("FacadeTester: FACADE TEST WAS FAILED: " + e.getMessage());
		}
		
		// Shut Down the Coupon System - CLOSE ALL CONNECTIONS and STOP THE DAILY TASK
		try	{
			Thread.sleep(5000);
			system.shutdown();
		}
		
		catch (MyException | InterruptedException e){
			System.err.println("FacadeTester: ERROR trying the SHUT DOWN the Coupon System from the TEST FACADE: " + e.getMessage());
		}
	}
}
