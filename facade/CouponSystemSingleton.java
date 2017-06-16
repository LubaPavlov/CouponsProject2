package imports.d20170427coupProjDafnaWeiss.facade;

import imports.d20170427coupProjDafnaWeiss.dao.CompanyDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.ConnectionPoolSingleton;
import imports.d20170427coupProjDafnaWeiss.dao.CouponDBDAO;
import imports.d20170427coupProjDafnaWeiss.dao.CustomerDBDAO;
import imports.d20170427coupProjDafnaWeiss.exceptions.MyException;
/**
 * The Project (Coupon System) Entry and End Point: 
 * Enter the system by singleton Login instance, Define the client type, Start the daily task and Shut down the system
 * @author Dafna Weiss
 * @version 1.0
 * @since 27.2.17
 */
public class CouponSystemSingleton extends Thread {
	
	private static CouponSystemSingleton instance = null;
	
	// loading the DAO Objects
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	
	// a private constructor
	private CouponSystemSingleton() {
				
		// starting the Daily Task Thread
		DailyCouponExpirationTask dailyTask = new DailyCouponExpirationTask();
		Thread dailyTaskThread = new Thread(dailyTask);
		dailyTaskThread.start();
		System.out.println("===============================");
		System.out.println("System: Starting the Daily Task");
		System.out.println("===============================");
	}
	
	// create a Single Instance of the LOGIN to the SYSTEM
	/**
	 * Create a singleton instance of the coupon system at the first time to enter the system
	 * @return The Coupon System singleton instance
	 */
	public static final CouponSystemSingleton getInstance(){
		if (instance == null){
			instance = new CouponSystemSingleton();
		}
		return instance;
	}
	
	// LOGIN - applying to the right facade by the client TYPE
	/**
	 * Define the client type and Login to the system with it
	 * @param name The name of the client who wants to enter the system
	 * @param password The password of the client who wants to enter the system
	 * @param clientType The type of the client who wants to enter the system: ADMIN/COMPANY/CUSTOMER
	 * @return The facade class that implements the CouponClientFacade interface according to the client type
	 * @throws MyException SQL exception
	 */
	public CouponClientFacade login (String name, String password, ClientType clientType) throws MyException {
		CouponClientFacade facade;
		
		// applying to the right facade by the client TYPE
		if (clientType.name().equals("ADMIN")){
			facade = new AdminFacade();		
		}
		
		else if (clientType.name().equals("COMPANY")){
			facade = new CompanyFacade();
		}
		
		else if (clientType.name().equals("CUSTOMER")){
			facade = new CustomerFacade();
		}
		else {
			facade = null;
			System.out.println("System: The CLIENT TYPE must be: ADMIN or COMPANY or CUSTOMER only!");
		}
		
		// Calling the LOGIN function according to the Client Type
		if(facade.login(name, password)){
			System.out.println("System: SUCCEED to LOGIN to FACADE: " + clientType);		
		}
		else {
			System.out.println("System: FAILED to LOGIN to FACADE: " + clientType);
			facade = null;
		}
		System.out.println("System: FACADE = " + clientType);
		return facade;
	}
	/**
	 * Shut Down the whole system: stop the Daily Task and closing all the connections to the SQL Database
	 * @throws MyException SQL exception
	 */
	public void shutdown()throws MyException {
		// stopping the Daily Task
		DailyCouponExpirationTask.stopTask();
		System.out.println("===========================");
		System.out.println("System: STOP the DAILY TASK");
		System.out.println("===========================");
		
		// closing all connections
		ConnectionPoolSingleton.closeAllConnections();
		System.out.println("System: SHUTTING DOWN all CONNECTIONS");
	}
	
}
