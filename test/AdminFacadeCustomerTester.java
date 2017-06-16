package imports.d20170427coupProjDafnaWeiss.test;

import java.util.Collection;

import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
import imports.d20170427coupProjDafnaWeiss.facade.AdminFacade;
import imports.d20170427coupProjDafnaWeiss.model.Customer;
/**
 * Test all the ADMIN Facade Customer's methods
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class AdminFacadeCustomerTester {
	/**
	 * Test all the Customer's methods of the ADMIN Facade
	 * @param adminFacade The ADMIN FACADE object
	 * @throws MyException SQL exception 
	 */
	public static void testAdminFacadeCustomer(AdminFacade adminFacade) throws MyException{
		//-----------------------------------------
		// Testing Customer Methods in ADMIN FACADE
		//-----------------------------------------
		System.out.println("");
		System.out.println("		********************************");
		System.out.println("		* A D M I N :  C U S T O M E R *");
		System.out.println("		********************************");
		
		// Testing CREATE CUSTOMER - return TRUE if succeed to CREATE a CUSTOMER
		System.out.println("");
		System.out.println("--------------CREATE CUSTOMER------------");
		
		Customer customer = new Customer("Dafna","1234");					// This Customer Name is already exist
		long id = 0;
		
		if (adminFacade.createCustomer(customer)){
			System.out.println("TestAdminFacadeCustomer: SUCCEED TO ADD A NEW CUSTOMER: " + customer.toString());	
		}
		
		else {
			System.out.println("TestAdminFacadeCustomer: FAILED ADD A CUSTOMER - please change the CUSTOMER NAME");
			
			// try to LOGIN again with another name
			customer = new Customer("Sarit","12345");						// This Customer name is OK - this will be the one to be deleted from the DB
			
			if (adminFacade.createCustomer(customer)){
				id = customer.getId();
				System.out.println("TestAdminFacadeCustomer: SUCCEED TO ADD A NEW CUSTOMER: " + customer.toString());	
			}
			else {
				System.out.println("TestAdminFacadeCustomer: FAILED ADD A CUSTOMER - please change the CUSTOMER NAME");
			}
			
			// try to LOGIN again with another name
			customer = new Customer("Ami","efgh");							// This Customer will be the one to be deleted as "new customer"
			
			if (adminFacade.createCustomer(customer)){
				System.out.println("TestAdminFacadeCustomer: SUCCEED TO ADD A NEW CUSTOMER: " + customer.toString());	
			}
			else {
				System.out.println("TestAdminFacadeCustomer: TEST FAILED IN CREATE CUSTOMER");
				return;
			}
		}
		System.out.println("-----------------------------------------");
		
		// Testing REMOVE CUSTOMER
		//-----------------------
		System.out.println("");
		System.out.println("--------------REMOVE CUSTOMER------------");
		
		// delete the new Customer that we created before
		adminFacade.removeCustomer(customer);
		System.out.println("TestAdminFacadeCustomer: SUCCEED TO REMOVE A NEW CUSTOMER: " + customer.toString());	
		
		// deleting a given Customer from the DB table
		customer = adminFacade.getCustomer(6);								// there is no Customer with this ID
		
		if (customer != null) {
			adminFacade.removeCustomer(customer);
			System.out.println("TestAdminFacadeCustomer: SUCCEED TO REMOVE A DB CUSTOMER: " + customer.toString()); 
		}
		
		else {
			System.out.println("TestAdminFacadeCustomer: FAILED TO REMOVE CUSTOMER: The CUSTOMER ID is INCORRECT");
			
			// try to remove another customer
			customer = adminFacade.getCustomer(id);							// This Customer's ID is OK
			
			if (customer != null) {
				adminFacade.removeCustomer(customer);
				System.out.println("TestAdminFacadeCustomer: SUCCEED TO REMOVE A DB CUSTOMER: " + customer.toString()); 
			}
			else {
				System.out.println("AdminFacadeCustomerTester: TEST FAILED IN REMOVE A CUSTOMER - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
					
		// Testing UPDATE Customer
		System.out.println("");
		System.out.println("--------------UPDATE CUSTOMER------------");
		
		customer = adminFacade.getCustomer(6);								// there is no Customer with this ID
		
		if (customer != null) {
			
			customer.setPassword("update2");
			adminFacade.updateCustomer(customer);
			
			System.out.println("TestAdminFacadeCustomer: SUCCEED TO UPDATE A CUSTOMER: " + customer.toString());
		}
		
		else {
			System.out.println("TestAdminFacadeCustomer: FAILED TO UPDATE CUSTOMER: The Customer ID is INCORRECT");
			
			// try to update another customer
			customer = adminFacade.getCustomer(3);							// This Customer's ID is OK	
			
			if (customer != null) {
				customer.setPassword("update3");
				adminFacade.updateCustomer(customer);
				
				System.out.println("TestAdminFacadeCustomer: SUCCEED TO UPDATE A CUSTOMER: " + customer.toString());
			}
			else {
				System.out.println("TestAdminFacadeCustomer: TEST FAILED trying to UPDATE A CUSTOMER - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
		
		// SHOW ALL CUSTOMERS
		//-------------------
		// checking if there is any Customer is the DB table
		Collection<Customer> customers = adminFacade.getAllCustomers();
		if (!customers.isEmpty()){
			System.out.println("");
			System.out.println("----------------------");
			System.out.println("ALL THE CUSTOMERS ARE:");
			System.out.println("----------------------");
			
			for (Customer customerList: customers){
				System.out.println(customerList);
			}
		}
		else {
			System.out.println("TestAdminFacadeCustomer: THE CUSTOMER TABLE IS EMPTY!");
		}
		
		// SHOW Customer by ID
		System.out.println("");
		System.out.println("-------------SHOW CUSTOMER BY ID---------");
		
		customer = adminFacade.getCustomer(6);							// there is no Customer with this ID
		
		if (customer != null) {
			System.out.println("TestAdminFacadeCustomer: CUSTOMER BY ID: " + customer);
		}
		
		else {
			System.out.println("TestAdminFacadeCustomer: The CUSTOMER ID is INCORRECT");
			
			// try to get another customer
			customer = adminFacade.getCustomer(1);						// This Customer's ID is OK	
			
			if (customer != null) {
				System.out.println("TestAdminFacadeCustomer: CUSTOMER BY ID: " + customer);
			}
			else {
				System.out.println("TestAdminFacadeCustomer: TEST FAILED trying to SHOW A CUSTOMER BY ID - second time");
				return;
			}
		}
		System.out.println("-----------------------------------------");
		System.out.println("");
		System.out.println("-------------END ADMIN-CUSTOMER----------");
		System.out.println("--------------END ADMIN FACADE-----------");
	}
}

