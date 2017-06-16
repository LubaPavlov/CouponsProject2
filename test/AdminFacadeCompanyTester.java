package imports.d20170427coupProjDafnaWeiss.test;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.model.Company;
/**
 * Test all the ADMIN Facade Company's methods
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class AdminFacadeCompanyTester {
	/**
	 * Test all the Company's methods of the ADMIN Facade
	 * @param adminFacade The ADMIN FACADE object
	 * @throws MyException SQL exception 
	 */
	public static void testAdminFacadeCompany(AdminFacade adminFacade) throws MyException{
		//----------------------------------------
		// Testing COMPANY Methods in ADMIN FACADE
		//----------------------------------------
		System.out.println("");
		System.out.println("		******************************");
		System.out.println("		* A D M I N :  C O M P A N Y *");
		System.out.println("		******************************");
		
		// Testing CREATE COMPANY - return TRUE if succeed to CREATE a COMPANY
		System.out.println("");
		System.out.println("--------------CREATE COMPANY-------------");
		
		Company company = new Company("BBB","abcd","bbb_comp@gmail.com");	// This Company Name is already exist
		long id = 0;
		
		if (adminFacade.createCompany(company)){
			System.out.println("TestAdminFacadeCompany: SUCCEED TO ADD A NEW COMPANY: " + company.toString());	
		}
		
		else {
			System.out.println("TestAdminFacadeCompany: FAILED ADD A COMPANY - please change the COMPANY NAME");
			
			// try to LOGIN again with another name
			company = new Company("Aroma","1234","aroma@gmail.com");			// This Company name is OK - this will be the one to be deleted from the DB
			
			if (adminFacade.createCompany(company)){
				id = company.getId();
				System.out.println("TestAdminFacadeCompany: SUCCEED TO ADD A NEW COMPANY: " + company.toString());	
			}
			else {
				System.out.println("TestAdminFacadeCompany: FAILED ADD A COMPANY - please change the COMPANY NAME");
			}
			
			company = new Company("Osem","abcd","osem@gmail.com");			// This Company will be the one to be deleted as "new company"
			
			if (adminFacade.createCompany(company)){
				System.out.println("TestAdminFacadeCompany: SUCCEED TO ADD A NEW COMPANY: " + company.toString());	
			}
			else {
				System.out.println("TestAdminFacadeCompany: TEST FAILED IN CREATE COMPANY - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
		
		// Testing REMOVE COMPANY
		//-----------------------
		System.out.println("");
		System.out.println("--------------REMOVE COMPANY-------------");
		
		// delete the new Company that we created before
		adminFacade.removeCompany(company);
		System.out.println("TestAdminFacadeCompany: SUCCEED TO REMOVE A NEW COMPANY: " + company.toString());	
		
		// deleting a given Company from the DB table
		company = adminFacade.getCompany(2);								// there is no COMPANY with this ID
		
		if (company != null) {
			
			adminFacade.removeCompany(company);
			System.out.println("TestAdminFacadeCompany: SUCCEED TO REMOVE A DB COMPANY: " + company.toString()); 
		}
		
		else {
			System.out.println("TestAdminFacadeCompany: FAILED TO REMOVE COMPANY: The COMPANY ID is INCORRECT");
			
			// try to remove another company
			company = adminFacade.getCompany(id);							// This COMPANY'S ID is OK
			
			if (company != null) {
				
				adminFacade.removeCompany(company);
				System.out.println("TestAdminFacadeCompany: SUCCEED TO REMOVE A DB COMPANY: " + company.toString()); 
			}
			else {
				System.out.println("TestAdminFacadeCompany: TEST FAILED IN REMOVE A COMPANY - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
					
		// Testing UPDATE COMPANY
		System.out.println("");
		System.out.println("--------------UPDATE COMPANY-------------");
		
		company = adminFacade.getCompany(2);								// there is no COMPANY with this ID
		
		if (company != null) {
			
			company.setPassword("update2");
			company.setEmail("update@gmail.com");
			adminFacade.updateCompany(company);
			
			System.out.println("TestAdminFacadeCompany: SUCCEED TO UPDATE A COMPANY: " + company.toString());
		}
		
		else {
			System.out.println("TestAdminFacadeCompany: FAILED TO UPDATE COMPANY: The COMPANY ID is INCORRECT");
			
			// try to update another company
			company = adminFacade.getCompany(3);							// This COMPANY'S ID is OK	
			
			if (company != null) {
				
				company.setPassword("update3");
				company.setEmail("update@gmail.com");
				adminFacade.updateCompany(company);
				
				System.out.println("TestAdminFacadeCompany: SUCCEED TO UPDATE A COMPANY: " + company.toString());
			}
			
			else {
				System.out.println("TestAdminFacadeCompany: TEST FAILED trying to UPDATE A COMPANY - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
				
		// SHOW ALL COMPANIES
		//-------------------
		// checking if there is any Company is the DB table
		Collection<Company> companies = adminFacade.getAllCompanies();
		if (!companies.isEmpty()){
			System.out.println("");
			System.out.println("----------------------");
			System.out.println("ALL THE COMPANIES ARE:");
			System.out.println("----------------------");
			
			for (Company companyList: companies){
				System.out.println(companyList);
			}
		}
		else {
			System.out.println("TestAdminFacadeCompany: THE COMPANY TABLE IS EMPTY!");
		}
		
		// SHOW COMPANY by ID
		System.out.println("");
		System.out.println("--------------SHOW COMPANY BY ID---------");
		
		company = adminFacade.getCompany(2);								// there is no COMPANY with this ID
		
		if (company != null) {
			System.out.println("TestAdminFacadeCompany: COMPANY BY ID: " + company);
		}
		
		else {
			System.out.println("TestAdminFacadeCompany: The COMPANY ID is INCORRECT");
			
			// try to get another company
			company = adminFacade.getCompany(1);							// This COMPANY'S ID is OK	
			
			if (company != null) {
				System.out.println("TestAdminFacadeCompany: COMPANY BY ID: " + company);
			}
			
			else {
				System.out.println("TestAdminFacadeCompany: TEST FAILED trying to SHOW A COMPANY BY ID - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
		System.out.println("");
		System.out.println("--------------END ADMIN-COMPANY----------");
	}
}
