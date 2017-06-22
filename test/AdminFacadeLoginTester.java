package imports.d20170427coupProjDafnaWeiss.test;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.facade.ClientType;
/**
 * Test all the ADMIN Facade Login method
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class AdminFacadeLoginTester {
	/**
	 * Test all the LOGIN method of the ADMIN Facade
	 * @return AdminFacade The ADMIN FACAD object
	 * @throws MyException SQL exception 
	 */
	public static AdminFacade adminLogin() throws MyException {
		
		System.out.println("*************************");
		System.out.println("*      A D M I N        *");
		System.out.println("*************************");
		
		System.out.println("");
		System.out.println("--------------ADMIN LOGIN-------------");
		
		ClientType clientType = ClientType.ADMIN;
		
		String adminName = "admin";
		String adminPassword = "5678";											// the password is INCORRECT
		
		AdminFacade adminFacade = (AdminFacade)FacadeTester.system.login(adminName,adminPassword,clientType);
		
		if (adminFacade != null){
			
			if (adminFacade.login(adminName, adminPassword)){
				System.out.println("TestAdminFacadeLogin: LOGIN to Coupon System as ADMIN - SUCCEED!");
				return adminFacade;
			}
			
			else {
				System.out.println("TestAdminFacadeLogin: THE ADMIN name or password are INCORRECT - please try again");
				return adminFacade = null;
			}
		}
				
		else {
			System.out.println("TestAdminFacadeLogin: ADMIN_FACADE IS NULL");
			
			// try to LOGIN with another password
			adminName = "admin";
			adminPassword = "1234";												// this password is OK
			adminFacade = (AdminFacade)FacadeTester.system.login(adminName,adminPassword,clientType);
			
			if (adminFacade.login(adminName, adminPassword)){
				System.out.println("TestAdminFacadeLogin: LOGIN to Coupon System as ADMIN - SUCCEED!");
				return adminFacade;
			}
			else {
				System.out.println("TestAdminFacadeLogin: TEST FAILED IN LOGIN ADMIN");
				return adminFacade = null;
			}
		}
	}
}
